package eus.ikeregana.invesnews_multimedia.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {

    @SerializedName("data")
    private List<ArticleDto> data;

    public List<ArticleDto> getData() {
        return data;
    }
}
