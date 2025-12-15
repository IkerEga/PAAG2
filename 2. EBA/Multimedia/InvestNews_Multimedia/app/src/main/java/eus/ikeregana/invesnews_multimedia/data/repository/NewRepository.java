package eus.ikeregana.invesnews_multimedia.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;
import eus.ikeregana.invesnews_multimedia.data.model.NewsResponse;
import eus.ikeregana.invesnews_multimedia.data.network.MarketauxApiService;
import eus.ikeregana.invesnews_multimedia.data.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final MarketauxApiService apiService;

    private final MutableLiveData<List<ArticleDto>> newsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public NewsRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<ArticleDto>> getNewsLiveData() {
        return newsLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchNews(String apiToken, String symbols, String search, String language, Integer page) {
        loadingLiveData.setValue(true);
        errorLiveData.setValue(null);

        apiService.getNews(apiToken, symbols, search, language, page)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        loadingLiveData.setValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            newsLiveData.setValue(response.body().getData());
                        } else {
                            errorLiveData.setValue("Respuesta inválida: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        loadingLiveData.setValue(false);
                        errorLiveData.setValue("Error de red: " + t.getMessage());
                    }
                });
    }
}
