package eus.ikeregana.supabase_proba;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
public class SupabaseClient {

    private static final String SUPABASE_URL = "https://dctgrlnjrvachrenyfrt.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRjdGdybG5qcnZhY2hyZW55ZnJ0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQxNzkyNDEsImV4cCI6MjA3OTc1NTI0MX0.EW91MCZm6dClWl0y52_m0zlCZO1c70E0jkTx-eCROuU";

    private static final OkHttpClient client = new OkHttpClient();

    public interface SupabaseCallback {
        void onSuccess (String json);
        void onError(String error);
    }

    public static void checkUser(String usuario, String password, SupabaseCallback callback) {
        String url = SUPABASE_URL + "/rest/v1/Usuario"
                + "?select=*"
                + "&usuario=eq." + usuario
                + "&password=eq." + password;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("HTTP " + response.code());
                    return;
                }
                String body = response.body().string();
                callback.onSuccess(body);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }
        });
    }


    public static void getBideojoko(SupabaseCallback callback) {
        String url = SUPABASE_URL + "/rest/v1/Bideojoko?select=*";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("HTTP " + response.code());
                    return;
                }
                callback.onSuccess(response.body().string());
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }
        });
    }
}
