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

package io.github.rysefoxx.plugin.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * A simple class to manage the Spring Spigot bootstrapper.
 *
 * @author Rysefoxx
 * @since 06.10.2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringSpigotBootstrapper {

  /**
   * Initializes the Spring boot context.
   *
   * @param plugin           The plugin instance
   * @param classLoader      The class loader to use
   * @param applicationClass The application class to use
   * @return The Spring boot context
   * @throws IllegalStateException If the context could not be initialized
   */
  public static @NotNull ConfigurableApplicationContext initialize(@NotNull JavaPlugin plugin,
                                                                   @NotNull ClassLoader classLoader,
                                                                   @NotNull Class<?> applicationClass) throws IllegalStateException {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(applicationClass);
    builder.web(WebApplicationType.NONE);
    return initialize(plugin, classLoader, builder).orElseThrow();
  }

  /**
   * Initializes the Spring boot context.
   *
   * @param plugin      The plugin instance
   * @param classLoader The class loader to use
   * @param builder     The Spring application builder
   * @return The Spring boot context
   * @throws IllegalStateException If the context could not be initialized
   */
  public static Optional<ConfigurableApplicationContext> initialize(@NotNull JavaPlugin plugin,
                                                                    @NotNull ClassLoader classLoader,
                                                                    @NotNull SpringApplicationBuilder builder) throws IllegalStateException {
    try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
      Future<ConfigurableApplicationContext> contextFuture = executor.submit(() -> {
        Thread.currentThread().setContextClassLoader(classLoader);

        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) {
          throw new IllegalStateException("Could not create the data directory: " + plugin.getDataFolder());
        }

        Properties props = new Properties();
        File file = new File(plugin.getDataFolder() + "/spring.properties");
        plugin.saveResource("spring.properties", false);

        try (FileInputStream inputStream = new FileInputStream(file)) {
          props.load(inputStream);
        } catch (IOException e) {
          plugin.getLogger().log(Level.WARNING, "Could not load the spring.properties file", e);
        }

        if (builder.application().getResourceLoader() == null) {
          DefaultResourceLoader loader = new DefaultResourceLoader(classLoader);
          builder.resourceLoader(loader);
        }

        return builder.properties(props).initializers(new SpringSpigotInitializer(plugin)).run();
      });
      return Optional.of(contextFuture.get());
    } catch (Exception e) {
      plugin.getLogger().log(Level.SEVERE, "Could not initialize the Spring boot context", e);
      Bukkit.shutdown();
      return Optional.empty();
    }
  }

}