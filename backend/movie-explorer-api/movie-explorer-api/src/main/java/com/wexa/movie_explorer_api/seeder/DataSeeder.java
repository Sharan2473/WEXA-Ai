package com.wexa.movie_explorer_api.seeder;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DataSeeder implements CommandLineRunner {

    private final Driver driver;

    public DataSeeder(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting Database Seeding...");

        // Cypher query to create the Movie, the Genre, and the relationship between them.
        // Using MERGE prevents creating duplicates if you restart the server.
        String cypherQuery = """
                UNWIND $movies AS movie
                MERGE (m:Movie {id: movie.id})
                SET m.title = movie.title,
                    m.releaseYear = movie.releaseYear,
                    m.plot = movie.plot,
                    m.posterUrl = movie.posterUrl
                
                MERGE (g:Genre {name: movie.genre})
                
                MERGE (m)-[:IN_GENRE]->(g)
                """;

        // Our list of mock movies transferred from the frontend
        List<Map<String, Object>> mockMovies = List.of(
            Map.of("id", "1", "title", "Inception", "genre", "Sci-Fi", "releaseYear", 2010, "plot", "A thief who steals corporate secrets through dream-sharing technology...", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5RO98r0C3gxSnCPelcM-G_MDYr_O4SQzinRHNFC_vX3CIbpzRHO6-c962&s=10"),
            Map.of("id", "2", "title", "Interstellar", "genre", "Sci-Fi", "releaseYear", 2014, "plot", "Explorers travel through a wormhole in space to ensure humanity's survival.", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSkUqb-86cLqozgook0Yj9QkHHAiSEqNGoHe3CgZYIrjuH8fOVC0kcKexd&s=10"),
            Map.of("id", "3", "title", "The Matrix", "genre", "Sci-Fi", "releaseYear", 1999, "plot", "A hacker learns the true nature of reality and his role in the war against its controllers.", "posterUrl", "https://m.media-amazon.com/images/I/613ypTLZHsL._AC_UF894,1000_QL80_.jpg"),
            Map.of("id", "4", "title", "The Dark Knight", "genre", "Action", "releaseYear", 2008, "plot", "Batman faces the Joker, a criminal mastermind seeking to plunge Gotham into anarchy.", "posterUrl", "https://irs.www.warnerbros.com/keyart-jpeg/movies/media/browser/the_dark_knight_key_art.jpg"),
            Map.of("id", "5", "title", "Pulp Fiction", "genre", "Crime", "releaseYear", 1994, "plot", "The lives of two mob hitmen, a boxer, and a pair of diner bandits intertwine.", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvZvrjMDrz9JEGdri2aouv0PN3EZKUza5vAgpNvZJDAMPecLJFDN6hApNR&s=10"),
            Map.of("id", "6", "title", "Fight Club", "genre", "Drama", "releaseYear", 1999, "plot", "An insomniac office worker and a devil-may-care soap maker form an underground fight club.", "posterUrl", "https://m.media-amazon.com/images/M/MV5BOTgyOGQ1NDItNGU3Ny00MjU3LTg2YWEtNmEyYjBiMjI1Y2M5XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg"),
            Map.of("id", "7", "title", "Forrest Gump", "genre", "Drama", "releaseYear", 1994, "plot", "The presidencies of Kennedy and Johnson, the Vietnam War, and more unfold through the perspective of an Alabama man.", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR28455MjUpQZkuCdqqeUbOBCyxuyWHIUgV_groI5v-qcAu0U8yDjtBuzyd&s=10"),
            Map.of("id", "8", "title", "Gladiator", "genre", "Action", "releaseYear", 2000, "plot", "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family.", "posterUrl", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p24674_p_v12_bc.jpg"),
            Map.of("id", "9", "title", "The Godfather", "genre", "Crime", "releaseYear", 1972, "plot", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "posterUrl", "https://m.media-amazon.com/images/M/MV5BNGEwYjgwOGQtYjg5ZS00Njc1LTk2ZGEtM2QwZWQ2NjdhZTE5XkEyXkFqcGc@._V1_.jpg"),
            Map.of("id", "10", "title", "Jurassic Park", "genre", "Adventure", "releaseYear", 1993, "plot", "A pragmatic paleontologist touring an almost complete theme park on an island in Central America is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.", "posterUrl", "https://images.contentstack.io/v3/assets/blt13adb7e2033fcee5/blt28a2b2c605dc2b39/6949fb5acedfd5e36f41f594/JurassicPark_Digital_Poster_2000x3000.jpg?width=2560"),
            Map.of("id", "11", "title", "Avatar", "genre", "Sci-Fi", "releaseYear", 2009, "plot", "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.", "posterUrl", "https://upload.wikimedia.org/wikipedia/en/d/d6/Avatar_%282009_film%29_poster.jpg?utm_source=en.wikipedia.org&utm_campaign=index&utm_content=original"),
            Map.of("id", "12", "title", "The Avengers", "genre", "Action", "releaseYear", 2012, "plot", "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.", "posterUrl", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p8815512_p_v10_ap.jpg"),
            Map.of("id", "13", "title", "Alien", "genre", "Horror", "releaseYear", 1979, "plot", "The crew of a commercial spacecraft encounter a deadly lifeform after investigating an unknown transmission.", "posterUrl", "https://cdn.kinocheck.com/i/wixnp473na.jpg"),
            Map.of("id", "14", "title", "Blade Runner", "genre", "Sci-Fi", "releaseYear", 1982, "plot", "A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2iS7WrVU_NLJILBBzxNthicK7FgCFIfdTawYfW_pBYXf6fAjo-BwYfZI&s=10"),
            Map.of("id", "15", "title", "Back to the Future", "genre", "Adventure", "releaseYear", 1985, "plot", "Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean invented by his close friend, the eccentric scientist Doc Brown.", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWC5ZPhrQ9N3U4ktTdQggk4lVkxgxbiWaquQZRD30xreigDD8NXPdlK4M&s=10"),
            Map.of("id", "16", "title", "The Lord of the Rings", "genre", "Fantasy", "releaseYear", 2001, "plot", "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", "posterUrl", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p28828_p_v8_ao.jpg"),
            Map.of("id", "17", "title", "Star Wars", "genre", "Sci-Fi", "releaseYear", 1977, "plot", "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station.", "posterUrl", "https://upload.wikimedia.org/wikipedia/en/0/0a/Star_Wars_%281997_re-release_poster%29.jpg"),
            Map.of("id", "18", "title", "The Terminator", "genre", "Action", "releaseYear", 1984, "plot", "A human soldier is sent from 2029 to 1984 to stop an almost indestructible cyborg killing machine, sent from the same year, which has been programmed to execute a young woman whose unborn son is the key to humanity's future salvation.", "posterUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZ7RROUayn_r0UfGxqTZNCi9ry1PQvyUTuPW02OZoGae1jrQY_orBANJM&s=10"),
            Map.of("id", "19", "title", "The Lion King", "genre", "Animation", "releaseYear", 1994, "plot", "Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.", "posterUrl", "https://upload.wikimedia.org/wikipedia/en/3/3d/The_Lion_King_poster.jpg?utm_source=en.wikipedia.org&utm_campaign=index&utm_content=original"),
            Map.of("id", "20", "title", "Titanic", "genre", "Romance", "releaseYear", 1997, "plot", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", "posterUrl", "https://m.media-amazon.com/images/I/81CVRAQi46L._AC_UF894,1000_QL80_.jpg"),
            Map.of("id", "21", "title", "Spider man", "genre", "Action", "releaseYear", 2002, "plot", "A shy teenager is bitten by a genetically modified spider and uses his new spider-like abilities to fight injustice as a masked superhero.", "posterUrl", "https://m.media-amazon.com/images/M/MV5BZWM0OWVmNTEtNWVkOS00MzgyLTkyMzgtMmE2ZTZiNjY4MmFiXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg")
        );

        try (Session session = driver.session()) {
            // 1. Completely wipe all existing nodes and relationships
            session.run("MATCH (n) DETACH DELETE n");
            
            // 2. Insert the fresh, clean list of 21 movies
            session.run(cypherQuery, Map.of("movies", mockMovies));
            System.out.println("Database wiped and successfully re-seeded with clean data!");
        } catch (Exception e) {
            System.err.println("Failed to seed database. Check your connection credentials.");
            e.printStackTrace();
        }
    }
}