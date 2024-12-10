import { createContext, useContext, useState } from 'react';
import PropTypes from 'prop-types';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [userType, setUserType] = useState(localStorage.getItem('userType'));
  const [username, setUsername] = useState(localStorage.getItem('username'));

  const login = (token, userType, username) => {
    localStorage.setItem('token', token);
    localStorage.setItem('userType', userType);
    localStorage.setItem('username', username);
    setToken(token);
    setUserType(userType);
    setUsername(username);
  };

  const logout = () => {
    localStorage.clear();
    setToken(null);
    setUserType(null);
    setUsername(null);
  };

  return (
    <AuthContext.Provider value={{ token, userType, username, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export const useAuth = () => useContext(AuthContext);
