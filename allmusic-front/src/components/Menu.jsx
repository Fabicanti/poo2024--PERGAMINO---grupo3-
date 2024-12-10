import PropTypes from 'prop-types';
import '../styles/style.css';

function Menu({ userType, onSelectOption }) {
  const menuItems = [
    { name: 'Todas las Canciones', key: 'all-songs', roles: ['Artist', 'Enthusiast'] },
    { name: 'Mis canciones', key: 'my-songs', roles: ['Artist'] },
    { name: 'Todas las Playlist', key: 'all-playlists', roles: ['Artist', 'Enthusiast'] },
    { name: 'Mis Playlist', key: 'my-playlists', roles: ['Artist', 'Enthusiast'] },
    { name: 'Buscar usuario', key: 'search-user', roles: ['Artist', 'Enthusiast'] },
  ];
  const filteredItems = menuItems.filter((item) => item.roles.includes(userType));

  return (
    <ul className="menu-list">
      {filteredItems.map((item) => (
        <li
          key={item.key}
          onClick={() => onSelectOption(item.key)}
          className="menu-item"
        >
          {item.name}
        </li>
      ))}
    </ul>
  );
}

Menu.propTypes = {
  userType: PropTypes.string.isRequired,
  onSelectOption: PropTypes.func.isRequired,
};

export default Menu;