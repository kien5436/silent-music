package phamkien.silentMusic.Services;

import java.util.List;

import phamkien.silentMusic.Models.AdModel;
import phamkien.silentMusic.Models.PlaylistModel;
import phamkien.silentMusic.Models.SongModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @GET("ads.php")
    Call<List<AdModel>> getAds();

    @GET("playlist.php")
    Call<List<PlaylistModel>> getPlaylists();

    @GET("song.php?hot_in_month=1")
    Call<List<SongModel>> getHotSongs();

    @GET("song.php")
    Call<List<SongModel>> getSongs();

    @GET("song.php")
    Call<SongModel> getSongById(@Query("song_id") String sid);

    @FormUrlEncoded
    @POST("song.php")
    Call<List<PlaylistModel>> getSongsInPlaylist(@Field("pid") String pid);

}
