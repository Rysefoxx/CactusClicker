package io.github.rysefoxx.infrastructure.persistence.mapper;

import io.github.rysefoxx.core.domain.model.CactusPlayer;
import io.github.rysefoxx.infrastructure.persistence.entity.CactusPlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting between CactusPlayer and CactusPlayerEntity.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = { AincraftMapper.class, FactoryMapper.class })
public interface CactusPlayerMapper {

  CactusPlayer toDomain(CactusPlayerEntity entity);

  CactusPlayerEntity toEntity(CactusPlayer domain);

}