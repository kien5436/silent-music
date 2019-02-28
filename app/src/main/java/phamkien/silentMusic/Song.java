package phamkien.silentMusic;

/**
 * Created by Hannibal Lecter on 02/20/19.
 */

public class Song {

    private long id;
    private String name;
    private String artist;
    private String album;
    private String cover;

    public Song(long id, String name, String artist) {
        this.id = id;
        this.artist = artist;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public long getId() {
        return id;
    }

    public String getCover() {
        return cover;
    }
}
