export default function Pagination({ currentPage, totalPages, onPageChange }) {
    return (
      <div className="pagination">
        {currentPage > 1 && (
          <button onClick={() => onPageChange(currentPage - 1)}>Previous</button>
        )}
        <span>Page {currentPage} of {totalPages}</span>
        {currentPage < totalPages && (
          <button onClick={() => onPageChange(currentPage + 1)}>Next</button>
        )}
      </div>
    );
  }