import { useState, useEffect } from 'react';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function AllPlaylists() {
  const [playlists, setPlaylists] = useState([]);
  const { token } = useAuth();

  useEffect(() => {
    fetch('http://localhost:7000/music/api/playlists', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setPlaylists(data))
      .catch((err) => console.error('Error fetching playlists:', err));
  }, [token]);

  return (
    <div>
      <h2>Todas las Playlists</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Creador</th>
            <th>Canciones</th>
          </tr>
        </thead>
        <tbody>
          {playlists.map((playlist) => (
            <tr key={playlist.id}>
              <td>{playlist.id}</td>
              <td>{playlist.name}</td>
              <td>{playlist.creator}</td>
              <td>{playlist.songs.length}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AllPlaylists;
