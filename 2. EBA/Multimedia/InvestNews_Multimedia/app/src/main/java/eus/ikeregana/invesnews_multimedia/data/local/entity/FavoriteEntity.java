package eus.ikeregana.invesnews_multimedia.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteEntity {

    @PrimaryKey
    @NonNull
    private String url;

    private String title;
    private String description;
    private String imageUrl;
    private String publishedAt;

    public FavoriteEntity(@NonNull String url, String title, String description, String imageUrl, String publishedAt) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
