package eus.ikeregana.invesnews_multimedia.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import android.content.Context;
import eus.ikeregana.invesnews_multimedia.data.local.AppDatabase;
import eus.ikeregana.invesnews_multimedia.data.local.FavoriteDao;
import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;
import eus.ikeregana.invesnews_multimedia.data.network.MarketauxApiService;
import eus.ikeregana.invesnews_multimedia.data.network.RetrofitClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eus.ikeregana.invesnews_multimedia.data.local.entity.FavoriteEntity;

public class NewsRepository {

    private final List<ArticleDto> accumulatedNews = new java.util.ArrayList<>();
    private final MarketauxApiService apiService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final FavoriteDao favoriteDao;

    private final MutableLiveData<List<ArticleDto>> news = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public NewsRepository(Context context) {
        apiService = RetrofitClient.getApiService();
        AppDatabase db = AppDatabase.getInstance(context);
        favoriteDao = db.favoriteDao();
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

        apiService.getNews(apiToken, symbols, search, language, page, 20)
                .enqueue(new retrofit2.Callback<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse>() {

                    @Override
                    public void onResponse(
                            retrofit2.Call<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse> call,
                            retrofit2.Response<eus.ikeregana.invesnews_multimedia.data.model.NewsResponse> response
                    ) {
                        loading.setValue(false);

                        if (response.isSuccessful() && response.body() != null) {

                            List<ArticleDto> newArticles = response.body().getData();

                            if (newArticles != null && !newArticles.isEmpty()) {
                                accumulatedNews.addAll(newArticles);
                            }

                            news.setValue(accumulatedNews);

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
    // FAVORITES (ROOM)

    public LiveData<List<FavoriteEntity>> getAllFavorites() {
        return favoriteDao.getAllFavorites();
    }

    public LiveData<Boolean> isFavorite(String url) {
        return favoriteDao.isFavorite(url);
    }

    public void addToFavorites(FavoriteEntity favorite) {
        executor.execute(() -> favoriteDao.insert(favorite));
    }

    public void removeFromFavorites(FavoriteEntity favorite) {
        executor.execute(() -> favoriteDao.delete(favorite));
    }



}


