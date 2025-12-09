package eus.ikeregana.supabase_proba;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLogin  = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Bete itzazu zelai guztiak", Toast.LENGTH_SHORT).show();
                } else {
                    SupabaseClient.checkUser(usuario, password, new SupabaseClient.SupabaseCallback() {
                        @Override
                        public void onSuccess(String json) {
                            runOnUiThread(() -> {
                                String trimmed = json.trim();
                                if (trimmed.equals("[]")) {
                                    Toast.makeText(MainActivity.this, "Erabiltzailea edo pasahitza okerra", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Login zuzena! ✅", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> {
                                Toast.makeText(MainActivity.this, "Errorea Supabase-rekin: " + error, Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hurrengo baten ikusiko dugu...", Toast.LENGTH_SHORT).show();
            }
        });


        SupabaseClient.getBideojoko(new SupabaseClient.SupabaseCallback() {
            @Override
            public void onSuccess(String json) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Konektatuta ", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Errorea konektatzean ", Toast.LENGTH_SHORT).show();
                });
            }
        });

    }
}