/*
 *      Copyright (c) 2023 Rysefoxx
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.rysefoxx.infrastructure.persistence.database;

import io.github.rysefoxx.application.port.out.CactusPlayerRepositoryPort;
import io.github.rysefoxx.application.service.LoadCactusPlayerUseCase;
import io.github.rysefoxx.application.service.SaveCactusPlayerUseCase;
import io.github.rysefoxx.application.service.UnloadCactusPlayerUseCase;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A simple class to manage the Spring Spigot auto configuration.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@ComponentScan(value = "io.github.rysefoxx")
@EntityScan(basePackages = "io.github.rysefoxx")
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement
public class SpringSpigotAutoConfiguration {

  /**
   * Bean for the {@link LoadCactusPlayerUseCase} class.
   * This class is used to load a CactusPlayer from the database.
   *
   * @param repository The CactusPlayerRepositoryPort to use
   * @return A new instance of LoadCactusPlayerUseCase
   */
  @Bean
  public LoadCactusPlayerUseCase loadCactusPlayerUseCase(@NotNull CactusPlayerRepositoryPort repository) {
    return new LoadCactusPlayerUseCase(repository);
  }

  /**
   * Bean for the {@link UnloadCactusPlayerUseCase} class.
   * This class is used to unload a CactusPlayer from the database.
   *
   * @param repository The CactusPlayerRepositoryPort to use
   * @return A new instance of UnloadCactusPlayerUseCase
   */
  @Bean
  public UnloadCactusPlayerUseCase unloadCactusPlayerUseCase(@NotNull CactusPlayerRepositoryPort repository) {
    return new UnloadCactusPlayerUseCase(repository);
  }

  /**
   * Bean for the {@link SaveCactusPlayerUseCase} class.
   * This class is used to save a CactusPlayer to the database.
   *
   * @param repository The CactusPlayerRepositoryPort to use
   * @return A new instance of SaveCactusPlayerUseCase
   */
  @Bean
  public SaveCactusPlayerUseCase saveCactusPlayerUseCase(@NotNull CactusPlayerRepositoryPort repository) {
    return new SaveCactusPlayerUseCase(repository);
  }

}