import { useState } from 'react';

function SearchBar({ onSearch, isLoading }) {
  const [query, setQuery] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (query.trim()) {
      onSearch(query.trim());
    }
  };

  return (
    <form onSubmit={handleSubmit} className="d-flex justify-content-center w-100 px-3">
      
      {/* Container for the relative positioning of the icon */}
      <div 
        className="position-relative shadow-sm rounded-pill" 
        style={{ 
          maxWidth: '600px', 
          width: '100%', 
          backgroundColor: '#f4f4f6' // Light grayish background from the image
        }}
      >
        
        {/* Purple Magnifying Glass Icon (Positioned Absolute on the left) */}
        <span 
          className="position-absolute d-flex align-items-center justify-content-center" 
          style={{ top: 0, bottom: 0, left: '20px', color: '#6f42c1' }} // Bootstrap purple
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
          </svg>
        </span>

        {/* The Input Field */}
        <input
          type="text"
          className="form-control form-control-lg rounded-pill"
          placeholder="Search for a movie..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          disabled={isLoading}
          style={{
            paddingLeft: '56px', // Extra padding to make room for the icon
            border: '2px solid #6f42c1', // Purple border matching the icon
            backgroundColor: 'transparent',
            color: '#198754', // Green text matching the reference image
            fontWeight: '500',
            boxShadow: 'none', // Removes default Bootstrap blue focus ring
            transition: 'all 0.3s ease'
          }}
        />

        {/* Small loading spinner on the right side when searching */}
        {isLoading && (
          <span 
            className="position-absolute d-flex align-items-center justify-content-center" 
            style={{ top: 0, bottom: 0, right: '20px', color: '#6f42c1' }}
          >
            <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
          </span>
        )}

      </div>
    </form>
  );
}

export default SearchBar;