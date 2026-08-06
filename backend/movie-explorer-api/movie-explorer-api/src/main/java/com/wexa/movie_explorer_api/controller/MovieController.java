package com.wexa.movie_explorer_api.controller;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final Driver driver;

    public MovieController(Driver driver) {
        this.driver = driver;
    }

    // Endpoint 1: Fetch all movies or search by title AND genre
    @GetMapping
    public List<Map<String, Object>> getMovies(@RequestParam(required = false) String search) {
        List<Map<String, Object>> movies = new ArrayList<>();
        
        // Updated Cypher query: Now checks if the search term is in the title OR the genre name
        String cypherQuery = search != null && !search.isEmpty() 
            ? "MATCH (m:Movie)-[:IN_GENRE]->(g:Genre) WHERE toLower(m.title) CONTAINS toLower($search) OR toLower(g.name) CONTAINS toLower($search) RETURN m.id AS id, m.title AS title, m.plot AS plot, m.posterUrl AS posterUrl, g.name AS genre"
            : "MATCH (m:Movie)-[:IN_GENRE]->(g:Genre) RETURN m.id AS id, m.title AS title, m.plot AS plot, m.posterUrl AS posterUrl, g.name AS genre";

        try (Session session = driver.session()) {
            var result = search != null && !search.isEmpty()
                ? session.run(cypherQuery, Values.parameters("search", search))
                : session.run(cypherQuery);

            while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> movieDetails = new HashMap<>();
                movieDetails.put("id", record.get("id").asString());
                movieDetails.put("title", record.get("title").asString());
                movieDetails.put("plot", record.get("plot").asString());
                movieDetails.put("posterUrl", record.get("posterUrl").asString());
                movieDetails.put("genre", record.get("genre").asString());
                
                movies.add(movieDetails);
            }
        }
        return movies;
    }

    // Endpoint 2: Fetch similar movies based on graph relationships
    @GetMapping("/{id}/similar")
    public List<Map<String, Object>> getSimilarMovies(@PathVariable String id) {
        List<Map<String, Object>> similarMovies = new ArrayList<>();
        
        // Match the target movie, traverse to its genre, and traverse BACK to other movies.
        String cypherQuery = """
            MATCH (m:Movie {id: $id})-[:IN_GENRE]->(g:Genre)<-[:IN_GENRE]-(similar:Movie)
            WHERE similar.id <> $id
            RETURN similar.id AS id, similar.title AS title, similar.plot AS plot, similar.posterUrl AS posterUrl, g.name AS genre
            LIMIT 3
            """;

        try (Session session = driver.session()) {
            var result = session.run(cypherQuery, Values.parameters("id", id));

            while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> movieDetails = new HashMap<>();
                movieDetails.put("id", record.get("id").asString());
                movieDetails.put("title", record.get("title").asString());
                movieDetails.put("plot", record.get("plot").asString());
                movieDetails.put("posterUrl", record.get("posterUrl").asString());
                movieDetails.put("genre", record.get("genre").asString());
                
                similarMovies.add(movieDetails);
            }
        }
        return similarMovies;
    }
}