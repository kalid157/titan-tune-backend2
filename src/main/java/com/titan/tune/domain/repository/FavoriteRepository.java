package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    Optional<Favorite> findByClientTrackingIdAndSongTrackingId(String clientId, String songId);
    List<Favorite> findByClientTrackingId(String clientId);
    void deleteByClientTrackingIdAndSongTrackingId(String clientId, String songId);
    long countBySongTrackingId(String songId);
}