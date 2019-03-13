<?php 
require 'server.php';

class Song extends Database {

	private $table = 'song';
	
	function __construct() {
		parent::__construct();
	}

	function search($kw) {
		
		$songs = $this->fetch("SELECT song_id, name, image, artists, liked, link from {$this->table} where name like '%$kw%' or artists like '%$kw%'");
		return json_encode($songs);
	}

	function getSongById($id) {
		
		$song = $this->fetch("SELECT song_id, name, image, artists, liked, link from {$this->table} where song_id = $id");
		return !empty($song) ? json_encode($song[0]) : json_encode(null);
	}

	function updateLike($sid) {
		
		self::$db->query("UPDATE song set liked = liked + 1 where song_id = $sid");
		return self::$db->affected_rows > 0 ? json_encode(true) : json_encode(false);
	}

	function getSongsInPlaylist($pid) {
		
		$songs = $this->fetch("SELECT song_id, name, image, artists, liked, link from {$this->table} where find_in_set('$pid', pid)");
		return json_encode($songs);
	}

	function getHotSongs() {

		$songs = $this->fetch("SELECT song_id, name, image, artists, liked, link from {$this->table} order by liked desc limit 5");
		return json_encode($songs);
	}

	function getSongs() {
		
		$songs = $this->fetch("SELECT song_id, name, image, artists, liked, link from {$this->table} limit 5");
		return json_encode($songs);
	}
}

header('content-type: application/json');
$song = new Song();

switch ($_SERVER['REQUEST_METHOD']) {
	case 'GET':
	if ( !empty($_SERVER['QUERY_STRING']) ) {
		
		if (isset($_GET['hot_in_month']) && $_GET['hot_in_month'] == 1)
			echo $song->getHotSongs();
		else if (isset($_GET['song_id']) && $_GET['song_id'] > 0)
			echo $song->getSongById($_GET['song_id']);
		else if (isset($_GET['keyword']) && !empty( trim($_GET['keyword']) ) )
			echo $song->search( trim($_GET['keyword']) );
		else {
			http_response_code(400);
			echo http_response_code();
		}
	}
	else echo $song->getSongs();
	break;
	case 'POST':
	if (isset($_POST['pid']))
		echo $song->getSongsInPlaylist($_POST['pid']);
	break;
}