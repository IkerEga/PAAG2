package eus.ikeregana.invesnews_multimedia.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import eus.ikeregana.invesnews_multimedia.data.local.entity.FavoriteEntity;


import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;
import eus.ikeregana.invesnews_multimedia.data.repository.NewsRepository;

public class NewsViewModel extends AndroidViewModel {

    private final NewsRepository repository;
    private boolean alreadyLoaded = false;


    public NewsViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(application);
    }

    public LiveData<List<ArticleDto>> getNews() {
        return repository.getNews();
    }

    public LiveData<Boolean> getLoading() {
        return repository.getLoading();
    }

    public LiveData<Integer> getLastFetchCount() {
        return repository.getLastFetchCount();
    }

    public LiveData<String> getError() {
        return repository.getError();
    }

    public void fetchNews(String apiToken, String symbols, String search, String language, Integer page) {
        repository.fetchNews(apiToken, symbols, search, language, page);
    }

    public void loadNewsIfNeeded(String apiToken, String symbols, String search, String language, Integer page) {
        if (page == null) page = 1;

        // Solo bloqueamos la primera carga para evitar duplicados al recrear el fragment
        if (page == 1) {
            if (alreadyLoaded) return;
            alreadyLoaded = true;
        }

        fetchNews(apiToken, symbols, search, language, page);
    }



    // FAVORITES (ROOM)

    public LiveData<List<FavoriteEntity>> getAllFavorites() {
        return repository.getAllFavorites();
    }

    public LiveData<Boolean> isFavorite(String url) {
        return repository.isFavorite(url);
    }

    public void addToFavorites(FavoriteEntity favorite) {
        repository.addToFavorites(favorite);
    }

    public void removeFromFavorites(FavoriteEntity favorite) {
        repository.removeFromFavorites(favorite);
    }


}
