package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, String> {
    List<Song> findByAlbumTrackingId(String albumId);
    List<Song> findByArtiste(String artiste);
}