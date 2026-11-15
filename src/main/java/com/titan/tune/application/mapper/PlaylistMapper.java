package com.titan.tune.application.mapper;

import com.titan.tune.application.dto.playlist.PlaylistDTO;
import com.titan.tune.domain.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {
    public PlaylistDTO toDto(Playlist playlist) {
        return new PlaylistDTO(
            playlist.getTrackingId(), playlist.getTitre(),
            playlist.getImageUrl(), playlist.getClientTrackingId(),
            playlist.getSongIds()
        );
    }
}