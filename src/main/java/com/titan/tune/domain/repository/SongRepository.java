package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, String> {
    List<Song> findByAlbumTrackingId(String albumId);
    List<Song> findByArtiste(String artisteId);

    @Query("SELECT s FROM Song s JOIN Category c ON s.categorieTrackingId = c.trackingId WHERE LOWER(c.nomCategorie) = LOWER(:nomCategorie)")
    List<Song> findByCategorieName(@Param("nomCategorie") String nomCategorie);
}