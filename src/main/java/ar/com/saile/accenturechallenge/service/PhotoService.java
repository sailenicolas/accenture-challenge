package ar.com.saile.accenturechallenge.service;

import ar.com.saile.accenturechallenge.dto.PhotoDto;

public interface PhotoService {

    PhotoDto getById(Long id);

    PhotoDto create(PhotoDto.Request photoDto);

    PhotoDto update(Long id, PhotoDto.Request photoDto);
}
