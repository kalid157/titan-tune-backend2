package com.titan.tune.application.mapper;

import com.titan.tune.application.dto.playlist.PlaylistDTO;
import com.titan.tune.domain.model.Playlist;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class PlaylistMapper {
    public PlaylistDTO toDto(Playlist playlist) {
        return new PlaylistDTO(
            playlist.getTrackingId(), 
            playlist.getTitre(),
            playlist.getImageUrl(), 
            playlist.getClientTrackingId(),
            //  Sécurité : si songIds est null, on renvoie une liste vide
            playlist.getSongIds() != null ? playlist.getSongIds() : new ArrayList<>()
        );
    }
}