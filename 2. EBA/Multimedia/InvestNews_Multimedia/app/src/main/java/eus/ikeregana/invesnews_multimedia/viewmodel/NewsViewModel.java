package eus.ikeregana.invesnews_multimedia.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;
import eus.ikeregana.invesnews_multimedia.data.repository.NewsRepository;

public class NewsViewModel extends ViewModel {

    private final NewsRepository repository;

    public NewsViewModel() {
        repository = new NewsRepository();
    }

    public LiveData<List<ArticleDto>> getNews() {
        return repository.getNews();
    }

    public LiveData<Boolean> getLoading() {
        return repository.getLoading();
    }

    public LiveData<String> getError() {
        return repository.getError();
    }

    public void fetchNews(String apiToken, String symbols, String search, String language, Integer page) {
        repository.fetchNews(apiToken, symbols, search, language, page);
    }
}
