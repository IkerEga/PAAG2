package eus.ikeregana.livedata;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Trainer {

    interface TrainerListener {
        void onCommand(String command);
    }

    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> training;

    // -------- Lógica de emisión cada segundo --------
    void beginTraining(TrainerListener trainerListener) {
        if (training == null || training.isCancelled()) {
            training = scheduler.scheduleWithFixedDelay(new Runnable() {
                int exercise;
                int repetitions = -1;

                @Override
                public void run() {
                    if (repetitions < 0) {
                        repetitions = random.nextInt(3) + 3; // 3..5 repeticiones
                        exercise = random.nextInt(5) + 1;    // ejercicios 1..5
                    }
                    String payload = "EXERCISE" + exercise + ":" + (repetitions == 0 ? "CHANGE" : repetitions);
                    trainerListener.onCommand(payload);
                    repetitions--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void stopTraining() {
        if (training != null) {
            training.cancel(true);
        }
    }

    // -------- LiveData: arranca al tener observer y para al no tenerlo --------
    public final LiveData<String> commandLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();
            beginTraining(command -> postValue(command)); // estamos en hilo fondo → postValue
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            stopTraining();
        }
    };
}
