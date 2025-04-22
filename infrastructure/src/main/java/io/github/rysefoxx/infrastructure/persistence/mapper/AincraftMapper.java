package io.github.rysefoxx.infrastructure.persistence.mapper;

import io.github.rysefoxx.core.domain.model.AincraftData;
import io.github.rysefoxx.infrastructure.persistence.entity.AincraftEmbeddable;
import org.mapstruct.Mapper;

/**
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Mapper(componentModel = "spring")
public interface AincraftMapper {
  AincraftEmbeddable toEmbeddable(AincraftData data);

  AincraftData toDomain(AincraftEmbeddable emb);
}
