import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from './AuthContext';
import '../styles/style.css';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const credentials = { username, password };

    try {
      const response = await fetch('http://localhost:7000/music/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials),
      });

      if (!response.ok) {
        throw new Error('Credenciales incorrectas');
      }

      const data = await response.json();
      console.log('Respuesta del backend:', data);

      login(data.token, data.userType);
      navigate('/dashboard');
    } catch (error) {
      console.error('Error de autenticación:', error);
      alert('Credenciales incorrectas, intente nuevamente.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Iniciar sesión</h2>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label htmlFor="username">Nombre de usuario</label>
            <input
              type="text"
              id="username"
              name="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="password">Contraseña</label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary btn-lg">
            Iniciar sesión
          </button>
        </form>
        <Link to="/register" className="btn btn-secondary btn-lg">
          Registrate
        </Link>
        <Link to="/" className="btn btn-secondary btn-lg">
          Volver al inicio
        </Link>
      </div>
    </div>
  );
}

export default Login;
