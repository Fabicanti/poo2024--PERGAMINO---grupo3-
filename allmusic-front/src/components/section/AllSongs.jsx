import { useState, useEffect } from 'react';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function AllSongs() {
  const [songs, setSongs] = useState([]);
  const [songId, setSongId] = useState('');
  const [filteredSong, setFilteredSong] = useState(null);
  const { token } = useAuth();

  useEffect(() => {
    fetch('http://localhost:7000/music/api/song/songs', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setSongs(data))
      .catch((err) => console.error('Error fetching songs:', err));
  }, [token]);

  const handleSearchById = () => {
    fetch(`http://localhost:7000/music/api/song/${songId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setFilteredSong(data))
      .catch((err) => console.error('Error fetching song by ID:', err));
  };

  return (
    <div>
      <h2>Todas las Canciones</h2>
      <div className="search-bar">
        <input
          type="text"
          placeholder="Buscar por ID"
          value={songId}
          onChange={(e) => setSongId(e.target.value)}
        />
        <button onClick={handleSearchById}>Buscar</button>
      </div>
      {filteredSong && (
        <div className="filtered-song">
          <p>ID: {filteredSong.id}</p>
          <p>Nombre: {filteredSong.name}</p>
          <p>Género: {filteredSong.genre}</p>
          <p>Artista: {filteredSong.artist}</p>
        </div>
      )}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Género</th>
            <th>Artista</th>
          </tr>
        </thead>
        <tbody>
          {songs.map((song) => (
            <tr key={song.id}>
              <td>{song.id}</td>
              <td>{song.name}</td>
              <td>{song.genre}</td>
              <td>{song.artist}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AllSongs;
