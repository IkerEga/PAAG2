package eus.ikeregana.invesnews_multimedia.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eus.ikeregana.invesnews_multimedia.data.local.entity.FavoriteEntity;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity favorite);

    @Delete
    void delete(FavoriteEntity favorite);

    @Query("SELECT * FROM favorites ORDER BY publishedAt DESC")
    LiveData<List<FavoriteEntity>> getAllFavorites();

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE url = :url LIMIT 1)")
    LiveData<Boolean> isFavorite(String url);
}
