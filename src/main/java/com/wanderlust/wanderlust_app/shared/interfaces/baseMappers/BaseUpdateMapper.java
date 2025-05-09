package com.wanderlust.wanderlust_app.shared.interfaces.baseMappers;

public interface BaseUpdateMapper<E, UpdateDTO>{
    E updateDTOToEntity(UpdateDTO updateDTO);
}
