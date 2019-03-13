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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private View view;
    private ArrayList<SongModel> songList;
    private Context context;

    public SearchAdapter(Context context, ArrayList<SongModel> songList) {
        this.songList = songList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        view = LayoutInflater.from(context).inflate(R.layout.adapter_song_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        SongModel song = songList.get(i);
        viewHolder.tvSongName.setText(song.getName());
        viewHolder.tvSongArtists.setText(song.getArtists());
        int imgSize = context.getResources().getInteger(R.integer.smallImg);
        Picasso.get().load(song.getImage()).resize(imgSize, imgSize).into(viewHolder.ivSongImage);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSongName, tvSongArtists;
        ImageView ivSongImage;

        public ViewHolder(View itemView) {

            super(itemView);
            tvSongArtists = itemView.findViewById(R.id.tvSongArtists);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(
                        new Intent(context, PlayerActivity.class)
                            .putExtra(context.getResources().getString(R.string.song), songList.get(getPosition()))
                    );

                    AppCompatActivity activity = (AppCompatActivity) context;
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            });
        }
    }
}