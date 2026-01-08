package quiz_with_rankig;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizServer {

    private static final int PORT = 12345;
    private static final List<ScoreEntry> ranking = new ArrayList<>();

    private static final String[] QUESTIONS = {
        "1) ¿Cuál de los siguientes es un protocolo de transporte orientado a la conexión?\n"
        + "a) TCP\nb) UDP\nc) IP",
        "2) ¿A qué tipo de aplicación beneficia más el protocolo UDP?\n"
        + "a) Transferencia de archivos\nb) Videoconferencia y transmisión de voz\nc) Navegación web",
        "3) ¿Cuál es la función del campo \"Reconocimiento\" (ACK) en la cabecera TCP?\n"
        + "a) Confirmación de entrega de datos\nb) Establecimiento de la conexión inicial\nc) Gestión de la derivación",
        "4) ¿Cuál de los siguientes es un protocolo de transporte sin conexión?\n"
        + "a) TCP\nb) UDP\nc) FTP",
        "5) ¿Qué tipo de servicio ofrece TCP con respecto a UDP?\n"
        + "a) Servicio no fiable\nb) Servicio fiable y orientado a la conexión\nc) Dirigido a la televisión"
    };

    private static final String[] CORRECT = {"a", "b", "a", "b", "b"};

    static class ScoreEntry {

        final String name;
        final int score;
        final long time; //El primero que entre al ranking con los mismos puntos se queda

        ScoreEntry(String name, int score, long time) {
            this.name = name;
            this.score = score;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("QuizServer running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Server error:");
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    Socket s = socket; BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                out.println("Welcome to QuickQuiz!");
                out.println("Enter your name:");

                String name = in.readLine();
                if (name == null) {
                    return;
                }
                name = name.trim();

                while (name.isEmpty()) {
                    out.println("Name cannot be empty. Enter your name:");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    name = name.trim();
                }

                Integer lastScore = null;
                boolean running = true;

                while (running) {
                    out.println("=== QUICKQUIZ MENU ===");
                    out.println("1) Start quiz (5 questions)");
                    out.println("2) View my last score");
                    out.println("3) View TOP 5 ranking");
                    out.println("4) Exit");
                    out.println("Choose option (1-4):");

                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }

                    int option;
                    try {
                        option = Integer.parseInt(line.trim());
                    } catch (NumberFormatException e) {
                        out.println("Invalid input. Choose 1-4.");
                        continue;
                    }

                    switch (option) {
                        case 1 -> {
                            int score = runQuiz(in, out);
                            lastScore = score;

                            addScore(name, score);

                            out.println("RESULT_START");
                            out.println("Quiz finished!");
                            out.println("Player: " + name);
                            out.println("Your score: " + score + " / " + QUESTIONS.length);
                            out.println("RESULT_END");
                        }
                        case 2 -> {
                            if (lastScore == null) {
                                out.println("You haven't taken the quiz yet.");
                            } else {
                                out.println("Your last score: " + lastScore + " / " + QUESTIONS.length);
                            }
                        }
                        case 3 -> {
                            out.println(getTop5Text());
                        }

                        case 4 -> {
                            out.println("Goodbye!");
                            running = false;
                        }
                        default ->
                            out.println("Option out of range. Choose 1-4.");
                    }
                }

            } catch (IOException e) {
                System.out.println("Client handler error: " + e.getMessage());
            }
        }

        private int runQuiz(BufferedReader in, PrintWriter out) throws IOException {
            int score = 0;

            for (int i = 0; i < QUESTIONS.length; i++) {
                out.println("QUESTION_START");
                out.println(QUESTIONS[i]);
                out.println("Tu respuesta (a/b/c): ");

                while (true) {
                    String answer = in.readLine();
                    if (answer == null) {
                        return score;
                    }

                    answer = answer.trim().toLowerCase();

                    if (!answer.equals("a") && !answer.equals("b") && !answer.equals("c")) {
                        out.println("Invalid. Answer only a, b or c.");
                        out.println("Tu respuesta (a/b/c): ");
                        continue;
                    }

                    if (answer.equals(CORRECT[i])) {
                        score++;
                    }
                    break;
                }
            }

            return score;
        }

        private static synchronized void addScore(String name, int score) {
            ranking.add(new ScoreEntry(name, score, System.nanoTime()));
        }

        private static synchronized String getTop5Text() {
            if (ranking.isEmpty()) {
                return "Ningun puntuaje todavia";
            }

            List<ScoreEntry> copy = new ArrayList<>(ranking);

            copy.sort(
                    java.util.Comparator
                            .comparingInt((ScoreEntry e) -> e.score).reversed()
                            .thenComparingLong(e -> e.time)
            );

            StringBuilder sb = new StringBuilder();
            sb.append("=== TOP 5 ===\n");
            int limit = Math.min(5, copy.size());

            for (int i = 0; i < limit; i++) {
                ScoreEntry e = copy.get(i);
                sb.append(i + 1).append(") ").append(e.name).append(" - ").append(e.score).append("/").append(QUESTIONS.length).append("\n");
            }

            return sb.toString();
        }

    }
}
