package phamkien.silentMusic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import phamkien.silentMusic.Activities.PlayerActivity;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;

public class HotSongsAdapter extends RecyclerView.Adapter<HotSongsAdapter.ViewHolder> {

    public static final String hotSong = "HOT_SONG";
    private ArrayList<SongModel> hotSongs;
    private Context context;

    public HotSongsAdapter(Context context, ArrayList<SongModel> hotSongs) {
        this.context = context;
        this.hotSongs = hotSongs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_song_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        final SongModel song = hotSongs.get(i);
        viewHolder.tvSongName.setText(song.getName());
        viewHolder.tvSongArtists.setText(song.getArtists());
        int imgSize = context.getResources().getInteger(R.integer.smallImg);
        Picasso.get().load(song.getImage()).resize(imgSize, imgSize).centerCrop().into(viewHolder.ivSongImage);
        viewHolder.tvLike.setText(song.getLiked());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(
                    new Intent(context, PlayerActivity.class)
                        .putExtra(HotSongsAdapter.hotSong, song)
                );

                AppCompatActivity activity = (AppCompatActivity) context;
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotSongs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSongName, tvSongArtists, tvLike;
        ImageView ivSongImage;

        public ViewHolder(View itemView) {

            super(itemView);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvSongArtists = itemView.findViewById(R.id.tvSongArtists);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);
            tvLike = itemView.findViewById(R.id.tvLike);
        }
    }
}
