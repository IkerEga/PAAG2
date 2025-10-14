package eus.ikeregana.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class TrainerViewModel extends AndroidViewModel {

    private final Trainer trainer;

    // LiveData expuesto a la vista
    private final LiveData<Integer> exerciseLiveData;
    private final LiveData<String> repetitionLiveData;

    public TrainerViewModel(@NonNull Application application) {
        super(application);

        trainer = new Trainer();

        // --- 1) Transformación a imagen (solo cuando cambia el ejercicio) ---
        final String[] prevExercise = new String[1]; // truco para “guardar estado” en lambda

        exerciseLiveData = Transformations.switchMap(trainer.commandLiveData, command -> {
            String exercise = command.split(":")[0]; // "EXERCISE1"..."EXERCISE5"

            if (prevExercise[0] == null || !prevExercise[0].equals(exercise)) {
                prevExercise[0] = exercise;

                int imageRes;
                switch (exercise) {
                    case "EXERCISE1":
                    default:
                        imageRes = R.drawable.e1; break;
                    case "EXERCISE2":
                        imageRes = R.drawable.e2; break;
                    case "EXERCISE3":
                        imageRes = R.drawable.e3; break;
                    case "EXERCISE4":
                        imageRes = R.drawable.e4; break;
                    case "EXERCISE5":
                        imageRes = R.drawable.e4b; break;
                }
                return new MutableLiveData<>(imageRes);
            }

            // si no cambia el ejercicio, devolvemos un LiveData “vacío” (no emite valor nuevo)
            return new MutableLiveData<>();
        });

        // --- 2) Transformación a repetición/CHANGE (cambia siempre) ---
        repetitionLiveData = Transformations.map(trainer.commandLiveData, command ->
                command.split(":")[1]  // "3", "2", "1" o "CHANGE"
        );
    }

    public LiveData<Integer> getExercise() {
        return exerciseLiveData;
    }

    public LiveData<String> getRepetition() {
        return repetitionLiveData;
    }
}
