package com.wanderlust.wanderlust_app.shared.interfaces.baseMappers;

public interface BaseEmbeddedMapper <E , EmbeddedDTO> {
    EmbeddedDTO toEmbeddedDTO(E entity);
}