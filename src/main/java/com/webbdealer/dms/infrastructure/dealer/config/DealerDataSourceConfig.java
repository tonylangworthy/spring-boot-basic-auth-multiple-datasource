package com.webbdealer.dms.infrastructure.dealer.config;

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
//@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dealerEntityManagerFactory",
        transactionManagerRef = "dealerTransactionManager",
        basePackages = {
                "com.webbdealer.dms.infrastructure.dealer.persistence"
        }
)
public class DealerDataSourceConfig {

    @Bean(name = "dealerDataSourceProperties")
    @ConfigurationProperties("dealer.datasource")
    public DataSourceProperties dealerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dealerDataSource")
    @ConfigurationProperties("dealer.datasource")
    public DataSource dealerDataSource() {
        return dealerDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "dealerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dealerDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.webbdealer.dms.infrastructure.dealer.persistence")
                .persistenceUnit("dealer")
                .build();
    }

    @Bean(name = "dealerTransactionManager")
    public PlatformTransactionManager dealerTransactionManager(
            @Qualifier("dealerEntityManagerFactory") EntityManagerFactory dealerEntityManagerFactory
    ) {
        return new JpaTransactionManager(dealerEntityManagerFactory);
    }
}
