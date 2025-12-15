package eus.ikeregana.invesnews_multimedia.data.model;

import com.google.gson.annotations.SerializedName;

public class ArticleDto {

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("published_at")
    private String publishedAt;

    // En Marketaux suele venir "source" como texto. Si luego vemos que es un objeto,
    // lo ajustamos, pero de momento esto es suficiente para arrancar.
    @SerializedName("source")
    private String source;

    public String getUuid() { return uuid; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
    public String getImageUrl() { return imageUrl; }
    public String getPublishedAt() { return publishedAt; }
    public String getSource() { return source; }
}
