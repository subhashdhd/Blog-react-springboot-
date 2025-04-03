import { Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

export default function Header() {
  const { user, logout } = useAuth();

  return (
    <header>
      <nav>
        <Link to="/">Home</Link>
        {user ? (
          <>
            <Link to="/create">Create Blog</Link>
            <button onClick={logout}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </nav>
    </header>
  );
}