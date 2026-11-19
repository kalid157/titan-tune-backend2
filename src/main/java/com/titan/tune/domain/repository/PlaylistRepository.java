package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    
    //  L'ASTUCE EST ICI : On dit à Hibernate de charger songIds tout de suite
    @Override
    @EntityGraph(attributePaths = {"songIds"})
    List<Playlist> findAll();

    @EntityGraph(attributePaths = {"songIds"})
    List<Playlist> findByClientTrackingId(String clientId);
    
    @EntityGraph(attributePaths = {"songIds"})
    List<Playlist> findByTitreContainingIgnoreCase(String titre);
}