<?php
require 'server.php';

class Playlist extends Database {

	private $table = 'playlist';
	
	function __construct() {
		parent::__construct();
	}

	function randPlaylists() {
		
		$playlist = $this->fetch("SELECT DISTINCT pid, name, bg_image FROM {$this->table} ORDER BY rand({date('Ymd')}) LIMIT 5");
		return json_encode($playlist);
	}
}

header('content-type: application/json');
$playlist = new Playlist();
echo $playlist->randPlaylists();