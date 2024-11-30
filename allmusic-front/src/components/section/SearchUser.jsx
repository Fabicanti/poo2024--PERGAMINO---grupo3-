import { useState } from 'react';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function SearchUser() {
  const [username, setUsername] = useState('');
  const [userResult, setUserResult] = useState(null);
  const [error, setError] = useState(null);
  const { token } = useAuth();

  const handleSearch = (e) => {
    e.preventDefault();
    setError(null);
    setUserResult(null);

    fetch(`http://localhost:7000/music/api/users/name/${username}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error('Usuario no encontrado o error en la solicitud');
        }
        return res.json();
      })
      .then((data) => {
        setUserResult(data);
      })
      .catch((err) => {
        setError(err.message);
      });
  };

  return (
    <div>
      <h2>Buscar Usuario por Nombre</h2>
      <form onSubmit={handleSearch}>
        <input
          type="text"
          placeholder="Ingrese el nombre de usuario"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <button type="submit">Buscar</button>
      </form>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {userResult && (
        <div>
          <h3>Resultado:</h3>
          <p><strong>ID:</strong> {userResult.id}</p>
          <p><strong>Nombre:</strong> {userResult.name}</p>
          <p><strong>Tipo:</strong> {userResult.type}</p>
        </div>
      )}
    </div>
  );
}

export default SearchUser;