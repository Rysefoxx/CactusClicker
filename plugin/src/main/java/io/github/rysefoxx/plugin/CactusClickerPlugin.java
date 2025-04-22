package io.github.rysefoxx.plugin;

import io.github.rysefoxx.infrastructure.persistence.database.CompoundClassLoader;
import io.github.rysefoxx.infrastructure.persistence.database.SpringApplication;
import io.github.rysefoxx.plugin.bootstrap.SpringSpigotBootstrapper;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main class of the CactusClicker plugin.
 *
 * @author Rysefoxx
 * @since 21.04.2025
 */
@Getter
public class CactusClickerPlugin extends JavaPlugin {

  private ConfigurableApplicationContext context;

  @Override
  public void onEnable() {
    initializeDatabase();
    registerEvents();
  }

  @Override
  public void onDisable() {
    if (this.context == null) {
      return;
    }
    this.context.close();
    this.context = null;
  }

  private void initializeDatabase() {
    List<ClassLoader> classLoaders = new ArrayList<>();
    classLoaders.add(0, getClass().getClassLoader());
    classLoaders.add(1, Thread.currentThread().getContextClassLoader());

    CompoundClassLoader loader = new CompoundClassLoader(classLoaders);
    this.context = SpringSpigotBootstrapper.initialize(this, loader, SpringApplication.class);
  }

  private void registerEvents() {
    this.context.getBeansOfType(Listener.class).forEach((s, listener) -> Bukkit.getPluginManager().registerEvents(listener, this));
  }
}