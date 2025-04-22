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

import java.util.HashMap;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * A simple class to manage the User Data auto configuration.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Configuration
@EnableJpaRepositories(basePackages = {"io.github.rysefoxx"},
    entityManagerFactoryRef = "gameModeEntityManager",
    transactionManagerRef = "gameModeTransactionManager")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDataAutoConfiguration {

  private final Environment env;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource gameModeDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean gameModeEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(gameModeDataSource());
    em.setPackagesToScan("io.github.rysefoxx");
    return getLocalContainerEntityManagerFactoryBean(em, env);
  }

  @Bean
  public PlatformTransactionManager gameModeTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(gameModeEntityManager().getObject());
    return transactionManager;
  }

  @Contract("_, _ -> param1")
  private @NotNull LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(@NotNull LocalContainerEntityManagerFactoryBean em, @NotNull Environment env) {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    HashMap<String, Object> properties = new HashMap<>();
    String ddlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto", env.getProperty("hibernate.hbm2ddl.auto", "none"));
    properties.put("hibernate.hbm2ddl.auto", ddlAuto);

    properties.put("hibernate.show_sql", true);
    properties.put("hibernate.format_sql", true);
    properties.put("hibernate.use_sql_comments", true);
    properties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
    em.setJpaPropertyMap(properties);
    return em;
  }
}