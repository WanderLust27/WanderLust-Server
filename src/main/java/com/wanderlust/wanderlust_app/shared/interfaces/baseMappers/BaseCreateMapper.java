package com.wanderlust.wanderlust_app.shared.interfaces.baseMappers;

public interface BaseCreateMapper<E , CreateDTO> {
    E toEntity(CreateDTO createDTO);
}