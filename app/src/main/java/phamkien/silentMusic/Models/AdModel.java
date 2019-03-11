package phamkien.silentMusic.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import phamkien.silentMusic.Services.APIService;

public class AdModel implements Serializable {

    @SerializedName("ad_image")
    @Expose
    private String adImage;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("song_id")
    @Expose
    private String songId;
    @SerializedName("song_name")
    @Expose
    private String songName;
    @SerializedName("song_image")
    @Expose
    private String songImage;

    public String getAdImage() {
        return APIService.getBaseUrl() + adImage;
    }

    public String getContent() {
        return content;
    }

    public String getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongImage() {
        return APIService.getBaseUrl() + songImage;
    }
}