package phamkien.silentMusic.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;

public class PlaylistInPlayerAdapter extends RecyclerView.Adapter<PlaylistInPlayerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SongModel> songList;

    public PlaylistInPlayerAdapter(Context context, ArrayList<SongModel> songList) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_song_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        SongModel song = songList.get(i);
        viewHolder.tvSongName.setText(song.getName());
        viewHolder.tvSongArtists.setText(song.getArtists());
        viewHolder.tvLike.setText(song.getLiked());
        int imgSize = context.getResources().getInteger(R.integer.smallImg);
        Picasso.get().load(song.getImage()).resize(imgSize, imgSize).centerCrop().into(viewHolder.ivSongImage);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSongName, tvSongArtists, tvLike;
        ImageView ivSongImage;

        public ViewHolder(View itemView) {

            super(itemView);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvSongArtists = itemView.findViewById(R.id.tvSongArtists);
            tvLike = itemView.findViewById(R.id.tvLike);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);
        }
    }
}
