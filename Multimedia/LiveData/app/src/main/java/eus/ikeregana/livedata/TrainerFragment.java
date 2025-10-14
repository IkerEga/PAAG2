package eus.ikeregana.livedata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import eus.ikeregana.livedata.databinding.FragmentTrainerBinding;

public class TrainerFragment extends Fragment {

    private FragmentTrainerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrainerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TrainerViewModel trainerViewModel =
                new ViewModelProvider(this).get(TrainerViewModel.class);

        // 1) Observa el ejercicio → carga drawable con Glide
        trainerViewModel.getExercise().observe(getViewLifecycleOwner(), exerciseResId -> {
            if (exerciseResId != null && exerciseResId != 0) {
                Glide.with(TrainerFragment.this)
                        .load(exerciseResId)
                        .into(binding.exercise);
            }
        });

        // 2) Observa la repetición/CHANGE → muestra texto y overlay
        trainerViewModel.getRepetition().observe(getViewLifecycleOwner(), repetition -> {
            if (repetition == null) return;

            binding.repetition.setText(repetition);

            if ("CHANGE".equals(repetition)) {
                binding.change.setVisibility(View.VISIBLE);
            } else {
                binding.change.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // evita memory leaks del binding
    }
}
