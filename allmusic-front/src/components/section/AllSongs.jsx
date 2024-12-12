import { useState, useEffect } from 'react';
import { Table, Form, Button } from 'react-bootstrap';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function AllSongs() {
  const [songs, setSongs] = useState([]);
  const [artistName, setArtistName] = useState('');
  const [genre, setGenre] = useState('');
  const { token } = useAuth();

  const fetchSongs = (artist = '', genre = '') => {
    let url = 'http://localhost:7000/music/api/song/songs';
    const params = new URLSearchParams();
    if (artist) params.append('artist', artist);
    if (genre) params.append('genre', genre);
    url += `?${params.toString()}`;

    fetch(url, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setSongs(data);
        } else {
          console.error('Expected array, got:', data);
        }
      })
      .catch((err) => console.error('Error fetching songs:', err));
  };

  useEffect(() => {
    fetchSongs();
  }, [token]);

  const handleSearch = () => {
    fetchSongs(artistName, genre);
  };

  return (
    <div>
      <h2>Todas las Canciones</h2>
      <Form inline={"true"} className="mb-3">
        <Form.Control
          type="text"
          placeholder="Buscar por Artista"
          value={artistName}
          onChange={(e) => setArtistName(e.target.value)}
          className="mr-2"
        />
        <Form.Control
          type="text"
          placeholder="Buscar por Género"
          value={genre}
          onChange={(e) => setGenre(e.target.value)}
          className="mr-2"
        />
        <Button onClick={handleSearch}>Buscar</Button>
      </Form>
      {Array.isArray(songs) && songs.length > 0 ? (
        <Table striped bordered hover responsive>
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
                <td>{song.artist.username}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      ) : (
        <p>No hay canciones disponibles.</p>
      )}
    </div>
  );
}

export default AllSongs;
