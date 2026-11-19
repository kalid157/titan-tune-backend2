package com.titan.tune.domain.service;

import com.titan.tune.application.dto.playlist.CreatePlaylistRequest;
import com.titan.tune.application.dto.playlist.PlaylistDTO;
import com.titan.tune.application.mapper.PlaylistMapper;
import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Playlist;
import com.titan.tune.domain.model.Song;
import com.titan.tune.domain.repository.PlaylistRepository;
import com.titan.tune.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final SongRepository songRepository; // ⭐ Ajouté pour récupérer les chansons
    @Transactional(readOnly = true)
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
                .songIds(new ArrayList<>()) // Initialisation
                .build();
        playlistRepository.save(playlist);
        return playlistMapper.toDto(playlist);
    }

    public void addSong(String playlistId, String songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist introuvable"));
        
        // Sécurité au cas où songIds serait null en base
        if (playlist.getSongIds() == null) {
            playlist.setSongIds(new ArrayList<>());
        }
        
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

    //  NOUVELLE MÉTHODE : Récupérer les chansons d'une playlist
    public List<Song> getSongsByPlaylist(String playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist introuvable"));
                
        if (playlist.getSongIds() == null || playlist.getSongIds().isEmpty()) {
            return new ArrayList<>();
        }
        
        return songRepository.findAllById(playlist.getSongIds());
    }

        public PlaylistDTO getPlaylistById(String id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist introuvable"));
        return playlistMapper.toDto(playlist);
    }
}