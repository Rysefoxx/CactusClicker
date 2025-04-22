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


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A classloader that combines multiple classloaders into one.
 */
@Getter
@RequiredArgsConstructor
public class CompoundClassLoader extends ClassLoader {

  private final Collection<ClassLoader> classLoaders;

  @Override
  public URL getResource(String name) {
    return this.classLoaders.stream()
        .filter(Objects::nonNull)
        .map(classLoader -> classLoader.getResource(name))
        .filter(Objects::nonNull).findFirst().orElse(null);
  }

  @Override
  public InputStream getResourceAsStream(String name) {
    return this.classLoaders.stream()
        .filter(Objects::nonNull)
        .map(classLoader -> classLoader.getResourceAsStream(name))
        .filter(Objects::nonNull).findFirst().orElse(null);
  }

  @Override
  public Enumeration<URL> getResources(String name) {
    List<URL> urls = new ArrayList<>();
    this.classLoaders.stream()
        .filter(Objects::nonNull)
        .map(classLoader -> {
          try {
            return classLoader.getResources(name);
          } catch (IOException e) {
            e.printStackTrace();
          }
          return null;
        })
        .filter(urlEnumeration -> Objects.nonNull(urlEnumeration) && urlEnumeration.hasMoreElements())
        .map(Enumeration::nextElement)
        .filter(url -> Objects.nonNull(url) && !urls.contains(url))
        .forEach(urls::add);

    return Collections.enumeration(urls);
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    for (ClassLoader loader : classLoaders) {
      if (loader != null) {
        try {
          return loader.loadClass(name);
        } catch (ClassNotFoundException ignore) {
        }
      }
    }
    throw new ClassNotFoundException();
  }

  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    return loadClass(name);
  }

  @Override
  public String toString() {
    return String.format("CompoundClassloader %s", classLoaders);
  }
}