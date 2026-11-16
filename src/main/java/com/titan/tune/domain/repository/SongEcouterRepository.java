package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.SongEcouter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SongEcouterRepository extends JpaRepository<SongEcouter, String> {
    @Query("SELECT s FROM SongEcouter s JOIN Song song ON s.songTrackingId = song.trackingId WHERE LOWER(song.titre) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<SongEcouter> searchBySongTitle(@Param("title") String title);
}