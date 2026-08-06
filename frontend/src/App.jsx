import { useState, useEffect } from 'react';
import SearchBar from './components/SearchBar';
import MovieCard from './components/MovieCard';
import MovieModal from './components/MovieModal';

function App() {
  const [movies, setMovies] = useState([]); // Start with an empty array
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [hasSearched, setHasSearched] = useState(false);
  
  // State to track which movie was clicked for the modal
  const [selectedMovie, setSelectedMovie] = useState(null); 

  // Use useEffect to load all movies when the app first starts
  useEffect(() => {
    fetchAllMovies();
  }, []);

  const fetchAllMovies = async () => {
    setIsLoading(true);
    setError(null);
    try {
      const response = await fetch('http://localhost:8080/api/movies');
      if (!response.ok) throw new Error("Failed to fetch");
      const data = await response.json();
      setMovies(data);
    } catch (err) {
      setError("Unable to connect to the database. Please try again later.");
    } finally {
      setIsLoading(false);
    }
  };

  const handleSearch = async (searchTerm) => { 
    setIsLoading(true);
    setError(null);
    setHasSearched(true);

    try {
      // If the search bar is empty, just fetch all movies again
      if (!searchTerm || searchTerm.trim() === '') {
        await fetchAllMovies();
        return;
      }

      // Hit our backend API with the genre query parameter
      // Note: We are using 'genre' here based on your backend setup. 
      // If your search bar was intended for titles, we would need a new endpoint!
      const response = await fetch(`http://localhost:8080/api/movies?search=${searchTerm}`);
      
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      setMovies(data);

    } catch (err) {
      setError("Unable to connect to the database. Please try again later.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container-fluid py-4 px-md-5 bg-light min-vh-100">
      <header className="text-center mb-4">
        <h1 className="display-4 fw-bold text-dark">Movie Graph Explorer</h1>
        <p className="lead text-secondary">Discover movies through shared connections.</p>
      </header>

      <main>
        {/* Sticky Search Bar Container */}
        <div className="sticky-top bg-light pt-3 pb-4 shadow-sm-bottom" style={{ zIndex: 1000 }}>
          <SearchBar onSearch={handleSearch} isLoading={isLoading} />
        </div>

        <div className="mt-4 pb-5">
          {/* Loading State */}
          {isLoading && (
            <div className="text-center my-5 py-5">
              <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Loading...</span>
              </div>
              <p className="mt-3 text-secondary fw-semibold">Traversing the graph...</p>
            </div>
          )}

          {/* Error State */}
          {error && (
            <div className="alert alert-danger text-center shadow-sm" role="alert">
              {error}
            </div>
          )}

          {/* Empty State */}
          {!isLoading && !error && hasSearched && movies.length === 0 && (
            <div className="alert alert-secondary text-center shadow-sm" role="alert">
              No movies found for that search. Try another!
            </div>
          )}

          {/* Populated State */}
          {!isLoading && !error && movies.length > 0 && (
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-5">
              {movies.map(movie => (
                <MovieCard 
                  key={movie.id} 
                  movie={movie} 
                  onClick={setSelectedMovie} // Passes the clicked movie up to state
                />
              ))}
            </div>
          )}
        </div>

        {/* Modal component rendered conditionally based on selectedMovie state */}
        <MovieModal 
          movie={selectedMovie} 
          onClose={() => setSelectedMovie(null)} 
          onMovieSelect={setSelectedMovie} // <-- Add this new line!
        />
      </main>
    </div>
  );
}

export default App;