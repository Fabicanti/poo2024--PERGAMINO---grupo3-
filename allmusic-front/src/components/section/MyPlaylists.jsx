import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function MyPlaylists() {
  const [playlists, setPlaylists] = useState([]);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const { token } = useAuth();

  useEffect(() => {
    fetch('http://localhost:7000/music/api/my-playlists', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setPlaylists(data))
      .catch((err) => console.error('Error fetching my playlists:', err));
  }, [token]);

  const handleDeletePlaylist = (id) => {
    fetch(`http://localhost:7000/music/api/playlists/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => setPlaylists((prev) => prev.filter((playlist) => playlist.id !== id)))
      .catch((err) => console.error('Error deleting playlist:', err));
  };

  const handleRenamePlaylist = (id, newName) => {
    fetch(`http://localhost:7000/music/api/playlists/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ name: newName }),
    })
      .then(() => {
        setPlaylists((prev) =>
          prev.map((playlist) =>
            playlist.id === id ? { ...playlist, name: newName } : playlist
          )
        );
      })
      .catch((err) => console.error('Error renaming playlist:', err));
  };

  return (
    <div>
      <h2>Mis Playlists</h2>
      <button onClick={() => setShowCreateForm(true)}>Crear Playlist</button>
      {showCreateForm && (
        <div className="create-form">
          <CreatePlaylistForm onClose={() => setShowCreateForm(false)} />
        </div>
      )}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Creador</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {playlists.map((playlist) => (
            <tr key={playlist.id}>
              <td>{playlist.id}</td>
              <td>{playlist.name}</td>
              <td>{playlist.creator}</td>
              <td>
                <button
                  onClick={() => handleRenamePlaylist(playlist.id, prompt('Nuevo nombre:'))}
                >
                  Renombrar
                </button>
                <button onClick={() => handleDeletePlaylist(playlist.id)}>Eliminar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function CreatePlaylistForm({ onClose }) {
  const [name, setName] = useState('');
  const { token } = useAuth();

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch('http://localhost:7000/music/api/playlists', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ name }),
    })
      .then((res) => res.json())
      .then(() => {
        onClose();
      })
      .catch((err) => console.error('Error creating playlist:', err));
  };

  return (
    <div className="form-modal">
      <form onSubmit={handleSubmit}>
        <h3>Crear Playlist</h3>
        <input
          type="text"
          placeholder="Nombre de la Playlist"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button type="submit">Crear</button>
        <button type="button" onClick={onClose}>
          Cancelar
        </button>
      </form>
    </div>
  );
}

CreatePlaylistForm.propTypes = {
  onClose: PropTypes.func.isRequired,
};

export default MyPlaylists;