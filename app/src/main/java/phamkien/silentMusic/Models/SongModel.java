package phamkien.silentMusic.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import phamkien.silentMusic.Services.APIService;

public class SongModel implements Parcelable {

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

    @SerializedName("song_id")
    @Expose
    private String songId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("artists")
    @Expose
    private String artists;
    @SerializedName("liked")
    @Expose
    private String liked;
    @SerializedName("link")
    @Expose
    private String link;

    protected SongModel(Parcel in) {
        songId = in.readString();
        name = in.readString();
        image = in.readString();
        artists = in.readString();
        liked = in.readString();
        link = in.readString();
    }

    public String getSongId() {
        return songId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return APIService.getBaseUrl() + image;
    }

    public String getArtists() {
        return artists;
    }

    public String getLiked() {

        int num = Integer.parseInt(liked);
        int remainder = 0;

        if (num >= 1000) {

            remainder = num % 1000;
            num /= 1000;
            liked = (remainder < 100 && remainder > 0) ? (num + "K" + remainder) : (num + "." + remainder + "K");
        }

        return liked;
    }

    public String getLink() {
        return APIService.getBaseUrl() + link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songId);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(artists);
        dest.writeString(liked);
        dest.writeString(link);
    }
}
