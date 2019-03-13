<?php
require 'server.php';

class Advertisement extends Database {

	private $table = 'advertisement';

	function __construct() {
		parent::__construct();
	}

	function getAds() {

		$ads = $this->fetch("SELECT ad.image as ad_image, ad.content, s.song_id, s.name as song_name, s.image as song_image FROM {$this->table} as ad JOIN song as s USING(song_id)");
		return json_encode($ads);
	}
}

header('content-type: application/json');
$ad = new Advertisement();
echo $ad->getAds();