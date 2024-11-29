import { Link } from 'react-router-dom';
import '../styles/style.css';

function Home() {
  return (
    <div className="home-container">
      <div className="home-content">
        <h1>Bienvenido a AllMusic</h1>
        <p>El lugar perfecto para gestionar tus canciones y playlists.</p>
        
        {/* Botón para iniciar sesión */}
        <Link to="/login">
          <button className="btn btn-primary btn-lg home-button">
            Iniciar sesión
          </button>
        </Link>

        {/* Botón para registrarse */}
        <Link to="/register">
          <button className="btn btn-secondary btn-lg home-button">
            Crear cuenta
          </button>
        </Link>
      </div>
    </div>
  );
}

export default Home;