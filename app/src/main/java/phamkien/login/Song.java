package phamkien.login;

/**
 * Created by Hannibal Lecter on 02/20/19.
 */

public class Song {

    private String name;
    private String singer;

    public Song(String name, String singer) {
        this.singer = singer;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {

        return singer;
    }
}
