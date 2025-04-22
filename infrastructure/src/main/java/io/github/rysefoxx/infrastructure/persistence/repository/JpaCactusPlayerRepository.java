package io.github.rysefoxx.infrastructure.persistence.repository;

import io.github.rysefoxx.infrastructure.persistence.entity.CactusPlayerEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing CactusPlayer entities.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Repository
public interface JpaCactusPlayerRepository extends JpaRepository<CactusPlayerEntity, UUID> {

}
