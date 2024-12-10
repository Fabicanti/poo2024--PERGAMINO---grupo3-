import { useState, useEffect } from 'react';
import { Modal, Button, Form, Table } from 'react-bootstrap';
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

function MySongs() {
  const [songs, setSongs] = useState([]);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [selectedSong, setSelectedSong] = useState(null);
  const [songName, setSongName] = useState('');
  const [songGenre, setSongGenre] = useState('');
  const [error, setError] = useState('');
  const { token, username } = useAuth();

  const fetchSongs = () => {
    if (!token || !username) return;
    fetch(`http://localhost:7000/music/api/song/me-songs`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Error fetching songs: ${res.statusText}`);
        }
        return res.json();
      })
      .then((data) => setSongs(data))
      .catch((err) => console.error('Error fetching my songs:', err));
  };

  useEffect(() => {
    fetchSongs();
  }, [token, username]);

  const handleDeleteSong = (id) => {
    if (!token) return;
    fetch(`http://localhost:7000/music/api/song/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Error deleting song: ${res.statusText}`);
        }
        setSongs((prev) => prev.filter((song) => song.id !== id));
      })
      .catch((err) => console.error('Error deleting song:', err));
  };

  const handleUpdateSong = (song) => {
    setSelectedSong(song);
    setSongName(song.name);
    setSongGenre(song.genre);
    setShowUpdateModal(true);
  };

  const handleCreateSong = () => {
    setSongName('');
    setSongGenre('');
    setShowCreateModal(true);
  };

  const closeCreateModal = () => {
    setShowCreateModal(false);
    setSongName('');
    setSongGenre('');
    setError('');
  };

  const closeUpdateModal = () => {
    setShowUpdateModal(false);
    setSelectedSong(null);
    setSongName('');
    setSongGenre('');
    setError('');
  };

  const handleSubmit = async (e, isUpdate = false) => {
    e.preventDefault();

    const url = isUpdate ? `http://localhost:7000/music/api/song/${selectedSong.id}` : 'http://localhost:7000/music/api/song';
    const method = isUpdate ? 'PUT' : 'POST';

    try {
      const res = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ name: songName, genre: songGenre, artist: username }),
      });

      if (!res.ok) throw new Error(`Error al ${isUpdate ? 'actualizar' : 'crear'} canción`);
      fetchSongs();
      isUpdate ? closeUpdateModal() : closeCreateModal();
    } catch (err) {
      console.error(`Error ${isUpdate ? 'updating' : 'creating'} song:`, err.message);
      setError(`Error al ${isUpdate ? 'actualizar' : 'crear'} la canción.`);
    }
  };

  return (
    <div>
      <h2>Mis Canciones</h2>
      <Button onClick={handleCreateSong}>Crear Canción</Button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <div className="table-container">
        <Table striped bordered hover responsive>
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
                  <Button onClick={() => handleUpdateSong(song)}>Modificar</Button>
                  <Button onClick={() => handleDeleteSong(song.id)}>Eliminar</Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>

      {/* Modal para crear canción */}
      <Modal show={showCreateModal} onHide={closeCreateModal}>
        <Modal.Header closeButton>
          <Modal.Title>Crear Canción</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={(e) => handleSubmit(e, false)}>
            <Form.Group>
              <Form.Label>Nombre de la Canción</Form.Label>
              <Form.Control
                type="text"
                value={songName}
                onChange={(e) => setSongName(e.target.value)}
                placeholder="Ingresa el nombre de la canción"
                isInvalid={!!error}
              />
              <Form.Control.Feedback type="invalid">
                {error}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group>
              <Form.Label>Género de la Canción</Form.Label>
              <Form.Control
                type="text"
                value={songGenre}
                onChange={(e) => setSongGenre(e.target.value)}
                placeholder="Ingresa el género de la canción"
                isInvalid={!!error}
              />
              <Form.Control.Feedback type="invalid">
                {error}
              </Form.Control.Feedback>
            </Form.Group>
            <Button variant="secondary" onClick={closeCreateModal}>
              Cancelar
            </Button>
            <Button variant="primary" type="submit">
              Crear
            </Button>
          </Form>
        </Modal.Body>
      </Modal>

      {/* Modal para actualizar canción */}
      <Modal show={showUpdateModal} onHide={closeUpdateModal}>
        <Modal.Header closeButton>
          <Modal.Title>Modificar Canción</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={(e) => handleSubmit(e, true)}>
            <Form.Group>
              <Form.Label>Nombre de la Canción</Form.Label>
              <Form.Control
                type="text"
                value={songName}
                onChange={(e) => setSongName(e.target.value)}
                placeholder="Ingresa el nombre de la canción"
                isInvalid={!!error}
              />
              <Form.Control.Feedback type="invalid">
                {error}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group>
              <Form.Label>Género de la Canción</Form.Label>
              <Form.Control
                type="text"
                value={songGenre}
                onChange={(e) => setSongGenre(e.target.value)}
                placeholder="Ingresa el género de la canción"
                isInvalid={!!error}
              />
              <Form.Control.Feedback type="invalid">
                {error}
              </Form.Control.Feedback>
            </Form.Group>
            <Button variant="secondary" onClick={closeUpdateModal}>
              Cancelar
            </Button>
            <Button variant="primary" type="submit">
              Guardar
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </div>
  );
}

export default MySongs;
