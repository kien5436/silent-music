package phamkien.silentMusic.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import phamkien.silentMusic.Services.APIService;

public class PlaylistModel implements Serializable {

    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bg_image")
    @Expose
    private String background;

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getBackground() {
        return APIService.getBaseUrl() + background;
    }
}
