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

import java.io.Serializable;
import java.util.ArrayList;

import phamkien.silentMusic.Activities.SongListActivity;
import phamkien.silentMusic.Models.PlaylistModel;
import phamkien.silentMusic.R;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {

    public static final String sPlaylist = "PLAYLIST";
    private ArrayList<PlaylistModel> playlists;
    private Context context;

    public PlaylistsAdapter(Context context, ArrayList<PlaylistModel> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_playlists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final PlaylistModel playlist = playlists.get(i);
        viewHolder.tvPlaylistName.setText(playlist.getName());
        int imgSize = context.getResources().getInteger(R.integer.largeImg);
        Picasso.get().load(playlist.getBackground()).resize(imgSize, imgSize).centerCrop().into(viewHolder.ivBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(
                    new Intent(context, SongListActivity.class)
                        .putExtra(PlaylistsAdapter.sPlaylist, playlist)
                );

                AppCompatActivity activity = (AppCompatActivity) context;
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlaylistName;
        ImageView ivBackground;

        public ViewHolder(View itemView) {

            super(itemView);
            tvPlaylistName = itemView.findViewById(R.id.tvPlaylistName);
            ivBackground = itemView.findViewById(R.id.ivBackground);
        }
    }
}
