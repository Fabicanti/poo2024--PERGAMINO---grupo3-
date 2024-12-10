import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import Register from './components/Register'; 
import Dashboard from './components/Dashboard';
import SearchUser from './components/section/SearchUser';
import { AuthProvider } from './components/AuthContext';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
            <Route path="/dashboard/search-user" element={<SearchUser />} />

          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
