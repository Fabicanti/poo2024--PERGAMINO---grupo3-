import PropTypes from 'prop-types';
import '../styles/style.css';

function Menu({ userType, onSelectOption }) {
  console.log('userType en Menu:', userType); // Debugging
  const menuItems = [
    { name: 'Todas las Canciones disponibles', key: 'all-songs', roles: ['artist', 'enthusiast'] },
    { name: 'Mis canciones', key: 'my-songs', roles: ['artist'] },
    { name: 'Todas las Playlist Creadas', key: 'all-playlists', roles: ['artist', 'enthusiast'] },
    { name: 'Mis Playlist', key: 'my-playlists', roles: ['artist', 'enthusiast'] },
    { name: 'Buscar usuario por nombre', key: 'search-user', roles: ['artist', 'enthusiast'] },
  ];
  const filteredItems = menuItems.filter((item) => item.roles.includes(userType));
  console.log('Items filtrados:', filteredItems); // Debugging

  return (
    <ul className="menu-list" style={{ border: '1px solid red' }}>
      {filteredItems.map((item) => (
        <li
          key={item.key}
          onClick={() => onSelectOption(item.key)}
          className="menu-item"
          style={{ backgroundColor: 'lightgray', border: '1px solid blue' }} // Debugging style
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