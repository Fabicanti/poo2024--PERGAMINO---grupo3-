import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Menu from './Menu';
import Section from './Section';
import { useAuth } from './AuthContext';
import '../styles/style.css';

function Dashboard() {
  const [selectedOption, setSelectedOption] = useState('');
  const navigate = useNavigate();
  const { userType, logout } = useAuth();

  useEffect(() => {
    console.log('userType en Dashboard:', userType);
    if (!userType) {
      navigate('/login');
    }
  }, [userType, navigate]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="dashboard">
      <div className="menu">
        <Menu userType={userType} onSelectOption={setSelectedOption} />
        <button className="logout-button" onClick={handleLogout}>
          Cerrar sesión
        </button>
      </div>
      <div className="content">
        <Section selectedOption={selectedOption} />
      </div>
    </div>
  );
}

export default Dashboard;
