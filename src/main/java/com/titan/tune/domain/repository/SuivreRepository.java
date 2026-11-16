package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.Suivre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SuivreRepository extends JpaRepository<Suivre, String> {
    Optional<Suivre> findByTrackingIdFollowerAndTrackingIdFollowing(String followerId, String followingId);
    List<Suivre> findByTrackingIdFollowing(String followingId); // Les followers d'un artiste
    List<Suivre> findByTrackingIdFollower(String followerId);   // Les artistes suivis par un user

    // Nouvelles méthodes pour compter
    long countByTrackingIdFollowing(String followingId); // Nombre de followers d'un artiste
    long countByTrackingIdFollower(String followerId);   // Nombre d'artistes suivis par un client
}