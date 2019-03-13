package phamkien.silentMusic.Services;

import java.util.List;

import phamkien.silentMusic.Models.AdModel;
import phamkien.silentMusic.Models.PlaylistModel;
import phamkien.silentMusic.Models.SongModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @Headers("Cache-Control: max-age=640000")
    @GET("ads.php")
    Call<List<AdModel>> getAds();

    @Headers("Cache-Control: max-age=640000")
    @GET("playlist.php")
    Call<List<PlaylistModel>> getPlaylists();

    @Headers("Cache-Control: max-age=640000")
    @GET("song.php?hot_in_month=1")
    Call<List<SongModel>> getHotSongs();

    @Headers("Cache-Control: max-age=640000")
    @GET("song.php")
    Call<SongModel> getSongById(@Query("song_id") String sid);

    @Headers("Cache-Control: max-age=640000")
    @GET("song.php")
    Call<List<SongModel>> search(@Query("keyword") String keyword);

    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @POST("song.php")
    Call<List<SongModel>> getSongsInPlaylist(@Field("pid") String pid);

}
