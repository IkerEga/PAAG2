package com.example.retrofit;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItunesViewModel extends AndroidViewModel {

    MutableLiveData<Itunes.Response> responseMutableLiveData = new MutableLiveData<>();

    public ItunesViewModel(@NonNull Application application) {
        super(application);
    }

    public void search(String text){
        Itunes.api.search(text).enqueue(new Callback<Itunes.Response>() {
            @Override
            public void onResponse(@NonNull Call<Itunes.Response> call, @NonNull Response<Itunes.Response> response) {
                responseMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Itunes.Response> call, @NonNull Throwable t) {
                Log.d("onFailure","Error on connection");
            }
        });
    }
}
