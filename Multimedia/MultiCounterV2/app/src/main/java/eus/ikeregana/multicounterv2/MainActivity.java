package eus.ikeregana.multicounterv2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import eus.ikeregana.multicounterv2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_K1 = "key_kontadorea1";
    private static final String KEY_K2 = "key_kontadorea2";
    private static final String KEY_K3 = "key_kontadorea3";
    private static final String KEY_K4 = "key_kontadorea4";

    private int kontadorea1;
    private int kontadorea2;
    private int kontadorea3;
    private int kontadorea4;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("KK", "onCreate() metodoan gaude");
        Toast.makeText(this, "onCreate() metodoan gaude", Toast.LENGTH_SHORT).show();

        // --- RESTAURAR ESTADO SI EXISTE ---
        if (savedInstanceState != null) {
            kontadorea1 = savedInstanceState.getInt(KEY_K1, 0);
            kontadorea2 = savedInstanceState.getInt(KEY_K2, 0);
            kontadorea3 = savedInstanceState.getInt(KEY_K3, 0);
            kontadorea4 = savedInstanceState.getInt(KEY_K4, 0);
        }

        renderAll();

        // Fila 1: txtClickKopurua5
        binding.btnGehitu8.setOnClickListener(v -> {
            kontadorea1++;
            binding.txtClickKopurua5.setText(String.valueOf(kontadorea1));
            updateTotal();
        });
        binding.btnReset1.setOnClickListener(v -> {
            kontadorea1 = 0;
            binding.txtClickKopurua5.setText(String.valueOf(kontadorea1));
            updateTotal();
        });

        // Fila 2: txtClickKopurua3
        binding.btnGehitu7.setOnClickListener(v -> {
            kontadorea2++;
            binding.txtClickKopurua3.setText(String.valueOf(kontadorea2));
            updateTotal();
        });
        binding.btnReset2.setOnClickListener(v -> {
            kontadorea2 = 0;
            binding.txtClickKopurua3.setText(String.valueOf(kontadorea2));
            updateTotal();
        });

        // Columna derecha fila 1
        binding.btnGehitu6.setOnClickListener(v -> {
            kontadorea3++;
            binding.txtClickKopurua2.setText(String.valueOf(kontadorea3));
            updateTotal();
        });
        binding.btnReset3.setOnClickListener(v -> {
            kontadorea3 = 0;
            binding.txtClickKopurua2.setText(String.valueOf(kontadorea3));
            updateTotal();
        });

        // Columna derecha fila 2
        binding.btnGehitu5.setOnClickListener(v -> {
            kontadorea4++;
            binding.txtClickKopurua1.setText(String.valueOf(kontadorea4));
            updateTotal();
        });
        binding.btnReset4.setOnClickListener(v -> {
            kontadorea4 = 0;
            binding.txtClickKopurua1.setText(String.valueOf(kontadorea4));
            updateTotal();
        });

        binding.btnResetAll.setOnClickListener(v -> {
            kontadorea1 = 0;
            kontadorea2 = 0;
            kontadorea3 = 0;
            kontadorea4 = 0;
            renderAll();
        });
    }

    // --- GUARDAR ESTADO ANTES DE DESTRUIRSE POR ROTACIÓN ---
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_K1, kontadorea1);
        outState.putInt(KEY_K2, kontadorea2);
        outState.putInt(KEY_K3, kontadorea3);
        outState.putInt(KEY_K4, kontadorea4);
    }

    private void updateTotal() {
        int total = kontadorea1 + kontadorea2 + kontadorea3 + kontadorea4;
        binding.txtClickKopurua4.setText(String.valueOf(total));
    }

    private void renderAll() {
        binding.txtClickKopurua5.setText(String.valueOf(kontadorea1));
        binding.txtClickKopurua3.setText(String.valueOf(kontadorea2));
        binding.txtClickKopurua2.setText(String.valueOf(kontadorea3));
        binding.txtClickKopurua1.setText(String.valueOf(kontadorea4));
        updateTotal();
    }
}
