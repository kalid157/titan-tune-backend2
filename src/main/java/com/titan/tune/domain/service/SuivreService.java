package com.titan.tune.domain.service;

import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.domain.model.Suivre;
import com.titan.tune.domain.repository.SuivreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuivreService {
    private final SuivreRepository suivreRepository;

    public void follow(String followerId, String artisteId) {
        if (followerId.equals(artisteId)) {
            throw new BusinessException("Vous ne pouvez pas vous suivre vous-même.");
        }
        if (suivreRepository.findByTrackingIdFollowerAndTrackingIdFollowing(followerId, artisteId).isPresent()) {
            throw new BusinessException("Vous suivez déjà cet artiste.");
        }
        Suivre suivre = Suivre.builder()
                .trackingId(UUID.randomUUID().toString())
                .trackingIdFollower(followerId)
                .trackingIdFollowing(artisteId)
                .build();
        suivreRepository.save(suivre);
    }

    public void unfollow(String followerId, String artisteId) {
        Suivre suivre = suivreRepository.findByTrackingIdFollowerAndTrackingIdFollowing(followerId, artisteId)
                .orElseThrow(() -> new BusinessException("Vous ne suivez pas cet artiste."));
        suivreRepository.delete(suivre);
    }

    public List<String> getFollowers(String artisteId) {
        return suivreRepository.findByTrackingIdFollowing(artisteId).stream()
                .map(Suivre::getTrackingIdFollower)
                .collect(Collectors.toList());
    }

    public List<String> getFollowings(String clientId) {
        return suivreRepository.findByTrackingIdFollower(clientId).stream()
                .map(Suivre::getTrackingIdFollowing)
                .collect(Collectors.toList());
    }
}