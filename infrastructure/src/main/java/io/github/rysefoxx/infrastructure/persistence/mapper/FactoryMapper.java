package io.github.rysefoxx.infrastructure.persistence.mapper;

import io.github.rysefoxx.core.domain.model.FactoryData;
import io.github.rysefoxx.infrastructure.persistence.entity.FactoryEmbeddable;
import org.mapstruct.Mapper;

/**
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Mapper(componentModel = "spring")
public interface FactoryMapper {
  FactoryEmbeddable toEmbeddable(FactoryData data);

  FactoryData toDomain(FactoryEmbeddable emb);
}