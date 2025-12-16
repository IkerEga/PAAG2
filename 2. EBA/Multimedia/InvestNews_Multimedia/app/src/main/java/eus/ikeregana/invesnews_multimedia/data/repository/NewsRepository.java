package eus.ikeregana.invesnews_multimedia.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;
import eus.ikeregana.invesnews_multimedia.data.network.MarketauxApiService;
import eus.ikeregana.invesnews_multimedia.data.network.RetrofitClient;

public class NewsRepository {

    private final MarketauxApiService apiService;

    private final MutableLiveData<List<ArticleDto>> news = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public NewsRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<ArticleDto>> getNews() {
        return news;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchNews(
            String apiToken,
            String symbols,
            String search,
            String language,
            Integer page
    ) {
        loading.setValue(true);
        error.setValue(null);

        apiService.getNews(apiToken, symbols, search, language, page)
                .enqueue(new retrofit2.Callback<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse>() {

                    @Override
                    public void onResponse(
                            retrofit2.Call<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse> call,
                            retrofit2.Response<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse> response
                    ) {
                        loading.setValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            news.setValue(response.body().getData());
                        } else {
                            error.setValue("Error API: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(
                            retrofit2.Call<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse> call,
                            Throwable t
                    ) {
                        loading.setValue(false);
                        error.setValue(t.getMessage());
                    }
                });
    }

}


