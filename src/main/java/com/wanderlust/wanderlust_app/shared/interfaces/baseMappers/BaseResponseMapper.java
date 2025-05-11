package com.wanderlust.wanderlust_app.shared.interfaces.baseMappers;

public interface BaseResponseMapper<E , ResponseDTO> {
    ResponseDTO toResponseDTO(E entity);
}

