package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Itunes {
    class Response {
        List<Content> results;
    }

    class Content {
        String artistName;
        String trackName;
        String artworkUrl100;
    }

    public interface Api {
        @GET("search/")
        Call<Response> search(@Query("term") String text);
    }

    public static Api api = new Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);
}
