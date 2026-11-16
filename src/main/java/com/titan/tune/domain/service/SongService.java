package com.titan.tune.domain.service;

import com.titan.tune.application.dto.song.SongDTO;
import com.titan.tune.application.mapper.SongMapper;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Song;
import com.titan.tune.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public List<SongDTO> getAllSongs() {
        return songRepository.findAll().stream()
                .map(songMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SongDTO> getSongsByAlbum(String albumId) {
        return songRepository.findByAlbumTrackingId(albumId).stream()
                .map(songMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SongDTO> getSongsByArtiste(String artisteId) {
        return songRepository.findByArtiste(artisteId).stream()
                .map(songMapper::toDto)
                .collect(Collectors.toList());
    }

    public SongDTO getSongById(String trackingId) {
        Song song = songRepository.findById(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Chanson introuvable : " + trackingId));
        return songMapper.toDto(song);
    }

    @Transactional
    public void deleteSong(String trackingId) {
        if (!songRepository.existsById(trackingId)) {
            throw new ResourceNotFoundException("Chanson introuvable : " + trackingId);
        }
        songRepository.deleteById(trackingId);
    }
}