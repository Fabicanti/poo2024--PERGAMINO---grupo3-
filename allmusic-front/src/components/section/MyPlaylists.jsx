import { useEffect, useState } from "react";
import { Modal, Button, Form , Table} from "react-bootstrap"; 
import { useAuth } from '../AuthContext';
import '../../styles/style.css';

const MyPlaylist = () => {
    const [playlists, setPlaylists] = useState([]);
    const [songs, setSongs] = useState([]);
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [showAddSongModal, setShowAddSongModal] = useState(false);
    const [showRenameModal, setShowRenameModal] = useState(false);
    const [newPlaylistName, setNewPlaylistName] = useState("");
    const [selectedPlaylistId, setSelectedPlaylistId] = useState(null);
    const [newSongId, setNewSongId] = useState("");
    const [newPlaylistNameForRename, setNewPlaylistNameForRename] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const { token } = useAuth();

    const openCreateModal = () => {
        console.log('Opening Create Playlist Modal');
        setShowCreateModal(true);
    };

    const closeCreateModal = () => {
        console.log('Closing Create Playlist Modal');
        setShowCreateModal(false);
        setNewPlaylistName("");
        setError("");
    };

    const openAddSongModal = (playlistId) => {
        console.log('Opening Add Song Modal for playlist:', playlistId);
        setSelectedPlaylistId(playlistId);
        setShowAddSongModal(true);
    };

    const closeAddSongModal = () => {
        console.log('Closing Add Song Modal');
        setShowAddSongModal(false);
        setNewSongId("");
        setError("");
    };

    const openRenameModal = (playlistId, currentName) => {
        console.log('Opening Rename Playlist Modal for playlist:', playlistId);
        setSelectedPlaylistId(playlistId);
        setNewPlaylistNameForRename(currentName);
        setShowRenameModal(true);
    };

    const closeRenameModal = () => {
        console.log('Closing Rename Playlist Modal');
        setShowRenameModal(false);
        setNewPlaylistNameForRename("");
        setError("");
    };

    const fetchApi = async (url, method = "GET", body = null) => {
        if (!token) {
            setError("You must be logged in.");
            return null;
        }

        const headers = {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json",
        };

        try {
            const response = await fetch(url, {
                method,
                headers,
                body: body ? JSON.stringify(body) : null,
            });

            if (response.status === 204) {
                return {};
            }

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP Error: ${response.status}`);
            }

            const text = await response.text();
            return text ? JSON.parse(text) : {};
        } catch (error) {
            console.error('API Error:', error);
            setError(error.message);
            return null;
        }
    };

    const loadPlaylists = async () => {
        setLoading(true);
        const data = await fetchApi('http://localhost:7000/music/api/playlist/me/playlists');
        if (data) {
            setPlaylists(data);
        }
        setLoading(false);
    };

    const loadSongs = async () => {
        const data = await fetchApi('http://localhost:7000/music/api/song/songs');
        if (data) {
            setSongs(data);
        }
    };

    const createPlaylist = async () => {
        if (!newPlaylistName) {
            setError("Playlist name cannot be empty.");
            return;
        }

        const data = await fetchApi('http://localhost:7000/music/api/playlist/playlist', 'POST', { name: newPlaylistName });
        if (data) {
            setShowCreateModal(false);
            setNewPlaylistName("");
            loadPlaylists();
        }
    };

    const addSongToPlaylist = async () => {
        if (!newSongId) {
            setError("Song ID cannot be empty.");
            return;
        }

        const data = await fetchApi(`http://localhost:7000/music/api/playlist/${selectedPlaylistId}/song`, 'POST', { songID: newSongId });
        if (data) {
            setShowAddSongModal(false);
            setNewSongId("");
            loadPlaylists();
        }
    };

    const renamePlaylist = async () => {
        if (!newPlaylistNameForRename) {
            setError("Playlist name cannot be empty.");
            return;
        }

        const data = await fetchApi(`http://localhost:7000/music/api/playlist/${selectedPlaylistId}`, 'PUT', { name: newPlaylistNameForRename });
        if (data) {
            setShowRenameModal(false);
            setNewPlaylistNameForRename("");
            loadPlaylists();
        }
    };

    const deletePlaylist = async (playlistId) => {
        const data = await fetchApi(`http://localhost:7000/music/api/playlist/${playlistId}`, 'DELETE');
        if (data) {
            loadPlaylists();
        }
    };

    const handleInputChange = (e) => setNewPlaylistName(e.target.value);
    const handleSongIdChange = (e) => setNewSongId(e.target.value);
    const handleRenameInputChange = (e) => setNewPlaylistNameForRename(e.target.value);

    useEffect(() => {
        loadPlaylists();
        loadSongs();
    }, []);

    return (
        <div>
            <h2>Mis Playlists</h2>
            <Button onClick={openCreateModal}>Crear Playlist</Button>
            {loading && <p>Cargando...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <div className="table-container">
                <Table striped bordered hover responsive>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {playlists.map((playlist) => (
                            <tr key={playlist.id}>
                                <td>{playlist.id}</td>
                                <td>{playlist.name}</td>
                                <td>
                                    <Button onClick={() => openAddSongModal(playlist.id)}>Agregar Canción</Button>
                                    <Button onClick={() => openRenameModal(playlist.id, playlist.name)}>Renombrar</Button>
                                    <Button onClick={() => deletePlaylist(playlist.id)}>Eliminar</Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
            {/* Modal para crear playlist */}
            <Modal show={showCreateModal} onHide={closeCreateModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Crear Playlist</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Nombre de la Playlist</Form.Label>
                            <Form.Control
                                type="text"
                                value={newPlaylistName}
                                onChange={handleInputChange}
                                placeholder="Ingresa el nombre de la playlist"
                                isInvalid={!!error}
                            />
                            <Form.Control.Feedback type="invalid">
                                {error}
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeCreateModal}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={createPlaylist}>
                        Crear
                    </Button>
                </Modal.Footer>
            </Modal>

            {/* Modal para agregar canción */}
            <Modal show={showAddSongModal} onHide={closeAddSongModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Agregar Canción a Playlist</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>ID de la Canción</Form.Label>
                            <Form.Control
                                type="text"
                                value={newSongId}
                                onChange={handleSongIdChange}
                                placeholder="Ingresa el ID de la canción"
                                isInvalid={!!error}
                            />
                            <Form.Control.Feedback type="invalid">
                                {error}
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Form>
                    <div className="song-list" style={{ maxHeight: '200px', overflowY: 'scroll' }}>
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
                                        <td>{song.artist.username}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeAddSongModal}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={addSongToPlaylist}>
                        Agregar
                    </Button>
                </Modal.Footer>
            </Modal>

            {/* Modal para renombrar playlist */}
            <Modal show={showRenameModal} onHide={closeRenameModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Renombrar Playlist</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Nuevo Nombre de la Playlist</Form.Label>
                            <Form.Control
                                type="text"
                                value={newPlaylistNameForRename}
                                onChange={handleRenameInputChange}
                                placeholder="Ingresa el nuevo nombre de la playlist"
                                isInvalid={!!error}
                            />
                            <Form.Control.Feedback type="invalid">
                                {error}
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeRenameModal}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={renamePlaylist}>
                        Renombrar
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default MyPlaylist;
