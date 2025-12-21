package eus.ikeregana.invesnews_multimedia.data.network;

import eus.ikeregana.invesnews_multimedia.data.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketauxApiService {

    @GET("v1/news/all")
    Call<NewsResponse> getNews(
            @Query("api_token") String apiToken,
            @Query("symbols") String symbols,
            @Query("search") String search,
            @Query("language") String language,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

}
