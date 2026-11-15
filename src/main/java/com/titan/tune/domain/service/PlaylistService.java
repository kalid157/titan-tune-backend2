package com.titan.tune.domain.service;

import com.titan.tune.application.dto.playlist.CreatePlaylistRequest;
import com.titan.tune.application.dto.playlist.PlaylistDTO;
import com.titan.tune.application.mapper.PlaylistMapper;
import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Playlist;
import com.titan.tune.domain.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    public List<PlaylistDTO> getAllPlaylists() {
        return playlistRepository.findAll().stream()
                .map(playlistMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlaylistDTO createPlaylist(String clientId, CreatePlaylistRequest request) {
        Playlist playlist = Playlist.builder()
                .trackingId(UUID.randomUUID().toString())
                .titre(request.titre())
                .imageUrl(request.imageUrl())
                .clientTrackingId(clientId)
                .songIds(new ArrayList<>())
                .build();
        playlistRepository.save(playlist);
        return playlistMapper.toDto(playlist);
    }

    public void addSong(String playlistId, String songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist introuvable"));
        if (playlist.getSongIds().contains(songId)) {
            throw new BusinessException("Chanson déjà dans la playlist.");
        }
        playlist.getSongIds().add(songId);
        playlistRepository.save(playlist);
    }

    public void deletePlaylist(String playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException("Playlist introuvable");
        }
        playlistRepository.deleteById(playlistId);
    }
}