import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function MySongs() {
  const [songs, setSongs] = useState([]);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const { token } = useAuth();

  useEffect(() => {
    fetch('http://localhost:7000/music/api/song/me-songs', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setSongs(data))
      .catch((err) => console.error('Error fetching my songs:', err));
  }, [token]);

  const handleDeleteSong = (id) => {
    fetch(`http://localhost:7000/music/api/song/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => setSongs((prev) => prev.filter((song) => song.id !== id)))
      .catch((err) => console.error('Error deleting song:', err));
  };

  return (
    <div>
      <h2>Mis Canciones</h2>
      <button onClick={() => setShowCreateForm(true)}>Crear Canción</button>
      {showCreateForm && (
        <div className="create-form">
          <CreateSongForm onClose={() => setShowCreateForm(false)} />
        </div>
      )}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Género</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {songs.map((song) => (
            <tr key={song.id}>
              <td>{song.id}</td>
              <td>{song.name}</td>
              <td>{song.genre}</td>
              <td>
                <button onClick={() => handleDeleteSong(song.id)}>Eliminar</button>
                <button>Actualizar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function CreateSongForm({ onClose }) {
  const [name, setName] = useState('');
  const [genre, setGenre] = useState('');
  const { token } = useAuth();
  const artist = localStorage.getItem('username'); // Recuperar el nombre del usuario

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch('http://localhost:7000/music/api/song', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ name, genre, artist }),
    })
      .then((res) => res.json())
      .then(() => {
        onClose();
      })
      .catch((err) => console.error('Error creating song:', err));
  };

  return (
    <div className="form-modal">
      <form onSubmit={handleSubmit}>
        <h3>Crear Canción</h3>
        <input
          type="text"
          placeholder="Nombre"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="text"
          placeholder="Género"
          value={genre}
          onChange={(e) => setGenre(e.target.value)}
        />
        <button type="submit">Crear</button>
        <button type="button" onClick={onClose}>
          Cancelar
        </button>
      </form>
    </div>
  );
}

CreateSongForm.propTypes = {
  onClose: PropTypes.func.isRequired,
};

MySongs.propTypes = {
  token: PropTypes.string.isRequired,
};

export default MySongs;
