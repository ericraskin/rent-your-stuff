package com.paslists.rys.entity;

public interface EntityRepository<DTO, Entity> {
    Entity save(DTO dto);
}
