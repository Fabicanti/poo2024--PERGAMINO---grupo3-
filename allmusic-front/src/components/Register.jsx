import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import '../styles/style.css';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [userType, setUserType] = useState('enthusiast');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const userData = {
      username,
      password,
    };

    let url = '';
    if (userType === 'enthusiast') {
      url = 'http://localhost:7000/music/api/enthusiast';
    } else if (userType === 'artist') {
      url = 'http://localhost:7000/music/api/artist';
    }

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      if (response.status === 201) {
        navigate('/login');
      } else if (response.status === 409) {
        setUsername('');
        setPassword('');
        setErrorMessage('El usuario ya existe. Intenta con otro nombre de usuario.');
      }
    } catch (error) {
      console.error('Error al registrar usuario:', error);
      setErrorMessage('Hubo un error al registrar el usuario.');
    }
  };

  return (
    <div className="create-user-container">
      <div className="create-user-box">
        <h2>Registrar Usuario</h2>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
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
          <div className="input-group">
            <label htmlFor="userType">Tipo de usuario</label>
            <select
              id="userType"
              name="userType"
              value={userType}
              onChange={(e) => setUserType(e.target.value)}
              required
            >
              <option value="enthusiast">Entusiasta</option>
              <option value="artist">Artista</option>
            </select>
          </div>
          <button type="submit" className="btn btn-primary btn-lg">
            Registrar
          </button>
        </form>
        <Link to="/" className="btn btn-secondary btn-lg">
          Volver al inicio
        </Link>
      </div>
    </div>
  );
}

export default Register;
