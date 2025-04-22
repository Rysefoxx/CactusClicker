package io.github.rysefoxx.infrastructure.persistence.adapter;

import io.github.rysefoxx.application.port.out.CactusPlayerRepositoryPort;
import io.github.rysefoxx.core.domain.model.CactusPlayer;
import io.github.rysefoxx.infrastructure.persistence.mapper.CactusPlayerMapper;
import io.github.rysefoxx.infrastructure.persistence.repository.JpaCactusPlayerRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CactusPlayerRepositoryAdapter implements CactusPlayerRepositoryPort {

  private final JpaCactusPlayerRepository repository;
  private final CactusPlayerMapper mapper;

  @Override
  public Optional<CactusPlayer> findById(@NotNull UUID uuid) {
    return this.repository.findById(uuid).map(mapper::toDomain);
  }

  @Override
  @Transactional
  public void save(@NotNull CactusPlayer cactusPlayer) {
    this.repository.save(this.mapper.toEntity(cactusPlayer));
  }
}