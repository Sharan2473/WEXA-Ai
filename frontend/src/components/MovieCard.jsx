function MovieCard({ movie, onClick }) {
  return (
    <div className="col">
      {/* 
        Bug 1 Fix: Added onClick={() => onClick(movie)} 
        Bug 2 Fix: Passing the entire 'movie' object ensures the modal receives the title, not just the plot.
        Added cursor: 'pointer' so the user knows it is clickable.
      */}
      <div 
        className="card h-100 shadow-sm border-0" 
        onClick={() => onClick(movie)}
        style={{ cursor: 'pointer', transition: 'transform 0.2s ease-in-out' }}
        onMouseOver={(e) => e.currentTarget.style.transform = 'scale(1.03)'}
        onMouseOut={(e) => e.currentTarget.style.transform = 'scale(1)'}
      >
        <img 
          src={movie.posterUrl} 
          className="card-img-top" 
          alt={movie.title} 
          style={{ height: '400px', objectFit: 'cover' }}
        />
        <div className="card-body d-flex flex-column align-items-center bg-light">
          
          {/* Displays the correct movie name */}
          <h5 className="card-title fw-bold text-dark text-center mb-3 text-uppercase">
            {movie.title}
          </h5>
          
          <div className="w-100 d-flex justify-content-between text-muted small px-3 mb-3">
            <span className="fw-bold">genre</span>
            <span className="text-uppercase fw-bold">{movie.genre}</span>
          </div>

          <hr className="w-100 my-0 mb-3 opacity-25" />

          {/* Displays the plot summary (truncated to 3 lines to keep cards equal height) */}
          <div className="w-100 text-start mt-auto">
            <span className="text-muted small fw-bold d-block mb-1">plot</span>
            <p 
              className="card-text small text-secondary" 
              style={{ display: '-webkit-box', WebkitLineClamp: 3, WebkitBoxOrient: 'vertical', overflow: 'hidden' }}
            >
              {movie.plot}
            </p>
          </div>

        </div>
      </div>
    </div>
  );
}

export default MovieCard;