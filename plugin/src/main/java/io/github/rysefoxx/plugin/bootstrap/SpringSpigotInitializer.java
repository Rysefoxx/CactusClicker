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

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * A simple class to manage the Spring Spigot initializer.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@RequiredArgsConstructor
public class SpringSpigotInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private final Plugin plugin;

  @Override
  public void initialize(@NotNull ConfigurableApplicationContext context) {
    val propertySource = context.getEnvironment().getPropertySources();
    val properties = new Properties();
    properties.put("spigot.plugin", plugin.getName());
    propertySource.addLast(new PropertiesPropertySource("spring-bukkit", properties));
  }
}