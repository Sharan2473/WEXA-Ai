package com.wexa.movie_explorer_api.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {

    @Value("${neo4j.uri}")
    private String uri;

    @Value("${neo4j.username}")
    private String username;

    @Value("${neo4j.password}")
    private String password;

    @Bean
    public Driver neo4jDriver() {
        // The assignment strictly requires handling cases where the DB might be unreachable.
        // We initialize the driver here. If the credentials or URI are wrong, 
        // it will throw an exception when the application tries to connect.
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}