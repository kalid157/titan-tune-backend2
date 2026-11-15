package com.titan.tune.domain.service;

import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.domain.model.Favorite;
import com.titan.tune.domain.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public void addFavorite(String clientId, String songId) {
        if (favoriteRepository.findByClientTrackingIdAndSongTrackingId(clientId, songId).isPresent()) {
            throw new BusinessException("Cette chanson est déjà dans vos favoris.");
        }
        Favorite fav = Favorite.builder()
                .trackingId(UUID.randomUUID().toString())
                .clientTrackingId(clientId)
                .songTrackingId(songId)
                .build();
        favoriteRepository.save(fav);
    }

    @Transactional
    public void removeFavorite(String clientId, String songId) {
        favoriteRepository.deleteByClientTrackingIdAndSongTrackingId(clientId, songId);
    }

    public List<String> getFavoriteSongIds(String clientId) {
        return favoriteRepository.findByClientTrackingId(clientId).stream()
                .map(Favorite::getSongTrackingId)
                .collect(Collectors.toList());
    }

    public long getSongLikeCount(String songId) {
        return favoriteRepository.countBySongTrackingId(songId);
    }
}