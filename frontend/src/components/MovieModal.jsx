import { useState, useEffect } from 'react';

function MovieModal({ movie, onClose, onMovieSelect }) {
  const [similarMovies, setSimilarMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (!movie) return;

    const fetchSimilarMovies = async () => {
      setIsLoading(true);
      try {
        const response = await fetch(`http://localhost:8080/api/movies/${movie.id}/similar`);
        if (response.ok) {
          const data = await response.json();
          setSimilarMovies(data);
        }
      } catch (error) {
        console.error("Failed to fetch similar movies:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchSimilarMovies();
  }, [movie]); 

  if (!movie) return null;

  return (
    <div className="modal show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.8)' }} tabIndex="-1" onClick={onClose}>
      <div className="modal-dialog modal-lg modal-dialog-centered" onClick={(e) => e.stopPropagation()}>
        <div className="modal-content shadow-lg border-0">
          
          {/* Modal Header */}
          <div className="modal-header bg-dark text-white border-0">
            <h5 className="modal-title fw-bold">{movie.title}</h5>
            <button type="button" className="btn-close btn-close-white" onClick={onClose} aria-label="Close"></button>
          </div>

          {/* Modal Body - Main Movie Info */}
          <div className="modal-body p-4 bg-light">
            <div className="row mb-4">
              <div className="col-md-4 text-center">
                <img src={movie.posterUrl} alt={movie.title} className="img-fluid rounded shadow-sm mb-3 mb-md-0" style={{ maxHeight: '300px' }} />
              </div>
              <div className="col-md-8">
                <span className="badge bg-primary mb-2 px-3 py-2">{movie.genre}</span>
                
                {/* BUG FIX 2: Replaced "Plot Summary" with the actual movie title */}
                <h4 className="mt-2 fw-semibold text-dark text-uppercase">{movie.title}</h4>
                
                <p className="text-secondary lh-lg mt-3">{movie.plot}</p>
              </div>
            </div>

            <hr className="my-4 text-muted" />

            {/* Recommendation Engine UI */}
            <h5 className="fw-bold mb-3 text-dark">You might also like...</h5>
            
            {isLoading ? (
              <div className="text-center py-3">
                <div className="spinner-border spinner-border-sm text-primary" role="status"></div>
                <span className="ms-2 text-muted small">Consulting the graph...</span>
              </div>
            ) : similarMovies.length > 0 ? (
              <div className="row g-3">
                {similarMovies.map((similar) => (
                  // BUG FIX 1: Added onClick and pointer styling so it acts like a button
                  <div 
                    key={similar.id} 
                    className="col-4 text-center"
                    onClick={() => onMovieSelect(similar)}
                    style={{ cursor: 'pointer', transition: 'transform 0.2s ease-in-out' }}
                    onMouseOver={(e) => e.currentTarget.style.transform = 'scale(1.05)'}
                    onMouseOut={(e) => e.currentTarget.style.transform = 'scale(1)'}
                  >
                    <img 
                      src={similar.posterUrl} 
                      alt={similar.title} 
                      className="img-fluid rounded shadow-sm mb-2" 
                      style={{ height: '120px', objectFit: 'cover', width: '100%' }} 
                    />
                    <p className="small fw-semibold text-truncate mb-0" title={similar.title}>
                      {similar.title}
                    </p>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-muted small">No connected movies found in the graph.</p>
            )}
          </div>
          
        </div>
      </div>
    </div>
  );
}

export default MovieModal;