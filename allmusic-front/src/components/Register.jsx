import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/style.css';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [userType, setUserType] = useState('entusiasta');
  const navigate = useNavigate();

  // Manejar el envío del formulario (aún no conecta con backend)
  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('Creando usuario con:', username, password, userType);
    // Aquí deberías hacer una petición al backend para crear el usuario.
    // Después de crear el usuario, puedes redirigir al login o a otra página.
    navigate('/login'); // Redirige al login después de crear el usuario
  };

  // Función para navegar a la página anterior
  const goBack = () => {
    navigate(-1); // Esto regresa al usuario a la página anterior
  };

  return (
    <div className="create-user-container">
      <div className="create-user-box">
        <h2>Crear nuevo usuario</h2>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label htmlFor="username">Nombre de usuario</label>
            <input
              type="text"
              id="username"
              name="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="password">Contraseña</label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="userType">Tipo de usuario</label>
            <select
              id="userType"
              name="userType"
              value={userType}
              onChange={(e) => setUserType(e.target.value)}
              required
            >
              <option value="entusiasta">Entusiasta</option>
              <option value="artista">Artista</option>
            </select>
          </div>
          <button type="submit" className="btn btn-primary btn-lg">
            Crear usuario
          </button>
        </form>

        {/* Botones para navegación */}
        <div className="button-group" style={{ marginTop: '1rem' }}>
          <button onClick={() => navigate('/login')} className="btn btn-secondary btn-lg">
            Ir al login
          </button>
          <button onClick={goBack} className="btn btn-secondary btn-lg">
            Volver
          </button>
        </div>
      </div>
    </div>
  );
}

export default Register;

