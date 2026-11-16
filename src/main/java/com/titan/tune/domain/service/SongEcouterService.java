package com.titan.tune.domain.service;

import com.titan.tune.domain.model.SongEcouter;
import com.titan.tune.domain.repository.SongEcouterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongEcouterService {
    private final SongEcouterRepository songEcouterRepository;

    public void registerListen(String userId, String songId) {
        SongEcouter ecouter = SongEcouter.builder()
                .trackingId(UUID.randomUUID().toString())
                .userTrackingId(userId)
                .songTrackingId(songId)
                .build();
        songEcouterRepository.save(ecouter);
    }

    public List<SongEcouter> searchByTitle(String title) {
        return songEcouterRepository.searchBySongTitle(title);
    }
}