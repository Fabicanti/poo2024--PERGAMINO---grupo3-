import PropTypes from 'prop-types';
import AllSongs from './section/AllSongs';
import MySongs from './section/MySongs';
import AllPlaylists from './section/AllPlaylists';
import MyPlaylists from './section/MyPlaylists';
import SearchUser from './section/SearchUser';
import '../styles/style.css';

function Section({ selectedOption }) {
  switch (selectedOption) {
    case 'all-songs':
      return <AllSongs />;
    case 'my-songs':
      return <MySongs />;
    case 'all-playlists':
      return <AllPlaylists />;
    case 'my-playlists':
      return <MyPlaylists />;
    case 'search-user':
      return <SearchUser />;
    default:
      return <AllSongs />;
  }
}

Section.propTypes = {
  selectedOption: PropTypes.string.isRequired,
};

export default Section;
