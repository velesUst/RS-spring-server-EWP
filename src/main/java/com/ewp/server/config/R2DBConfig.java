package com.ewp.server.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.pool.ConnectionPool;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import java.time.Duration; 


@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.ewp.server.persistenceR2DB.entety"})
@EnableJpaRepositories(
    basePackages = "com.ewp.server.persistenceR2DB.repository"/*,
    entityManagerFactoryRef = "sysEntityManager", 
    transactionManagerRef = "sysTransactionManager"*/
)

public class R2DBConfig {

    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {

        // 1. using ConnectionFactories.get from url
        //ConnectionFactory factory = ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        // 2. using r2dbc drivers provided tools to create a connection factory.

        PostgresqlConnectionConfiguration configuration =
            PostgresqlConnectionConfiguration.builder()
            .host(env.getProperty("r2dbc.host"))
            .port(Integer.parseInt(env.getProperty("r2dbc.port")) )
            .database(env.getProperty("r2dbc.base"))
            .username(env.getProperty("r2dbc.username"))
            .password(env.getProperty("r2dbc.password"))                        
            //.codecRegistrar(EnumCodec.builder().withEnum("post_status", Post.Status.class).build())
            .build();

        ConnectionFactory fg = new PostgresqlConnectionFactory(configuration);
        ConnectionPoolConfiguration connectionPoolConfiguration =
        ConnectionPoolConfiguration.builder(fg)
            .maxIdleTime(Duration.ofMillis( Long.parseLong(env.getProperty("r2dbc.pool.max-idle-time")) ))
            .initialSize(Integer.parseInt(env.getProperty("r2dbc.pool.initial-size")))
            .maxSize(Integer.parseInt(env.getProperty("r2dbc.pool.max-size")))
            .build();
            //spring.r2dbc.pool.enabled=true
            //spring.data.r2dbc.repositories.enabled=true

        return new ConnectionPool(connectionPoolConfiguration);
    }

    @Bean
    TransactionAwareConnectionFactoryProxy transactionAwareConnectionFactoryProxy(ConnectionFactory connectionFactory) {
        return new TransactionAwareConnectionFactoryProxy(connectionFactory);
    }

    @Bean
    DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                //.bindMarkers(() -> BindMarkersFactory.named(":", "", 20).create())
                .namedParameters(true)
                .build();
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }

    /*@Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }*/

}