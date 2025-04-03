import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

export default function RegisterPage() {
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    password: ''
  });
  const [error, setError] = useState('');
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await register(userData);
    if (result.success) {
      navigate('/login');
    } else {
      setError(result.message);
    }
  };

  return (
    <div className="register-page">
      <h2>Register</h2>
      {error && <div className="error">{error}</div>}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={userData.name}
          onChange={(e) => setUserData({...userData, name: e.target.value})}
          placeholder="Name"
          required
        />
        <input
          type="email"
          value={userData.email}
          onChange={(e) => setUserData({...userData, email: e.target.value})}
          placeholder="Email"
          required
        />
        <input
          type="password"
          value={userData.password}
          onChange={(e) => setUserData({...userData, password: e.target.value})}
          placeholder="Password"
          required
        />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}