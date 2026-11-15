package com.titan.tune.application.mapper;

import com.titan.tune.application.dto.song.SongDTO;
import com.titan.tune.domain.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    public SongDTO toDto(Song song) {
        return new SongDTO(
            song.getTrackingId(), song.getTitre(), song.getAudio(), song.getArtiste(),
            song.getAlbumTrackingId(), song.getArtisteTrackingId(),
            song.getDurationSeconds(), song.getIsVip()
        );
    }
}