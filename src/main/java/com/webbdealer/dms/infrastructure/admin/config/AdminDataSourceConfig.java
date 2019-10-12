package com.webbdealer.dms.infrastructure.admin.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "adminEntityManagerFactory",
        transactionManagerRef = "adminTransactionManager",
        basePackages = {
                "com.webbdealer.dms.infrastructure.admin.persistence"
        }
)
public class AdminDataSourceConfig {

    @Primary
    @Bean(name = "adminDataSourceProperties")
    @ConfigurationProperties("admin.datasource")
    public DataSourceProperties adminDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "adminDataSource")
    @ConfigurationProperties("com.webbdealer.dms.infrastructure.admin.config")
    public DataSource adminDataSource() {
        return adminDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "adminEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    adminEntityManagerFactory(
            EntityManagerFactoryBuilder builder
    ) {
        System.out.println("Displaying data source...");
        System.out.println(adminDataSource());
        return builder
                .dataSource(adminDataSource())
                .packages("com.webbdealer.dms.infrastructure.admin.persistence")
                .persistenceUnit("admin")
                .build();
    }

    @Primary
    @Bean(name = "adminTransactionManager")
    public PlatformTransactionManager adminTransactionManager(
            final @Qualifier("adminEntityManagerFactory") LocalContainerEntityManagerFactoryBean adminEntityManagerFactory
    ) {
        return new JpaTransactionManager(adminEntityManagerFactory.getObject());
    }
}