import { useEffect, useState } from "react";
import { Table, Form, Button } from "react-bootstrap";
import { useAuth } from "../AuthContext";
import "../../styles/style.css";

function SearchUser() {
    const [username, setUsername] = useState("");
    const [userResult, setUserResult] = useState(null);
    const [userList, setUserList] = useState([]);
    const [error, setError] = useState(null);
    const { token } = useAuth();

    const handleSearch = (e) => {
        e.preventDefault();
        setError(null);
        setUserResult(null);

        if (!username.trim()) {
            setError("Ingrese un nombre de usuario para buscar.");
            return;
        }

        fetch(`http://localhost:7000/music/api/${username}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Usuario no encontrado o error en la solicitud.");
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

    const fetchAllUsers = () => {
        setError(null);

        fetch("http://localhost:7000/music/api", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Error al obtener la lista de usuarios.");
                }
                return res.json();
            })
            .then((data) => {
                setUserList(data);
            })
            .catch((err) => {
                setError(err.message);
            });
    };

    useEffect(() => {
        fetchAllUsers();
    }, [token]);

    return (
        <div>
            <h2>Buscar Usuario</h2>
            <Form inline className="mb-3" onSubmit={handleSearch}>
                <Form.Control
                    type="text"
                    placeholder="Ingrese el nombre de usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="mr-2"
                />
                <Button type="submit">Buscar</Button>
            </Form>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {userResult && (
                <div>
                    <h3>Resultado:</h3>
                    <p><strong>ID:</strong> {userResult.id}</p>
                    <p><strong>Nombre:</strong> {userResult.username}</p>
                    <p><strong>Tipo:</strong> {userResult.userType}</p>
                </div>
            )}

            <h3>Lista de Usuarios</h3>
            {userList.length > 0 ? (
                <Table striped bordered hover responsive>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Tipo</th>
                        </tr>
                    </thead>
                    <tbody>
                        {userList.map((user) => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.username}</td>
                                <td>{user.userType}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            ) : (
                <p>No hay usuarios registrados.</p>
            )}
        </div>
    );
}

export default SearchUser;
