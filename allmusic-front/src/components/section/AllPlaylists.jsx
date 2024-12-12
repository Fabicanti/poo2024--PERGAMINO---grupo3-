import { useState, useEffect } from 'react';
import { Table, Form, Button } from 'react-bootstrap';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function AllPlaylists() {
  const [playlists, setPlaylists] = useState([]);
  const [playlistDetail, setPlaylistDetail] = useState(null);
  const [searchId, setSearchId] = useState(''); 
  const [error, setError] = useState(null); 
  const { token } = useAuth();

  useEffect(() => {
    fetch('http://localhost:7000/music/api/playlist/playlists', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Error HTTP: ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        setPlaylists(data);
        setError(null);
      })
      .catch((err) => {
        console.error('Error fetching playlists:', err);
        setError(err.message);
      });
  }, [token]);

  const fetchPlaylistById = () => {
    if (!searchId.trim()) {
      setError('Por favor, ingresa un ID válido.');
      return;
    }
    fetch(`http://localhost:7000/music/api/playlist/${searchId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Error HTTP: ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        setPlaylistDetail(data);
        setError(null);
      })
      .catch((err) => {
        console.error('Error fetching playlist by ID:', err);
        setError(err.message);
      });
  };

  return (
    <div>
      <h2>Playlists</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {/* Buscar por ID */}
      <Form inline={"true"} className="mb-3">
        <Form.Control
          type="text"
          placeholder="Buscar por ID"
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
          className="mr-2"
        />
        <Button onClick={fetchPlaylistById}>Buscar</Button>
      </Form>

      {/* Mostrar detalle de una playlist */}
      {playlistDetail && (
        <div>
          <h3>Detalles de la Playlist</h3>
          <p>ID: {playlistDetail.id}</p>
          <p>Nombre: {playlistDetail.name}</p>
          <p>Canciones: {playlistDetail.songs?.length || 0}</p>
          <ul>
            {playlistDetail.songs?.map((song, index) => (
              <li key={index}>{song.name}</li>
            ))}
          </ul>
        </div>
      )}

      {/* Mostrar tabla de playlists */}
      <h3>Todas las Playlists</h3>
      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Cantidad de Canciones</th>
          </tr>
        </thead>
        <tbody>
          {playlists.map((playlist) => (
            <tr key={playlist.id}>
              <td>{playlist.id}</td>
              <td>{playlist.name}</td>
              <td>{playlist.songCount}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

export default AllPlaylists;
