package com.titan.tune.domain.service;

import com.titan.tune.application.dto.album.AlbumDTO;
import com.titan.tune.application.dto.album.UpdateAlbumAccessRequest;
import com.titan.tune.application.mapper.AlbumMapper;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Album;
import com.titan.tune.domain.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public List<AlbumDTO> getAllAlbums() {
        return albumRepository.findAll().stream()
                .map(albumMapper::toDto)
                .collect(Collectors.toList());
    }

    public AlbumDTO updateAccess(String albumId, UpdateAlbumAccessRequest request) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album introuvable"));
        if (request.isFree() != null) album.setIsFree(request.isFree());
        if (request.isVip() != null) album.setIsVip(request.isVip());
        albumRepository.save(album);
        return albumMapper.toDto(album);
    }
}