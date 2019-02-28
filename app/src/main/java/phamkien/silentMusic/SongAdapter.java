package phamkien.silentMusic;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hannibal Lecter on 02/19/19.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Context c;
    private ArrayList<Song> songList;

    public SongAdapter(Context c, ArrayList<Song> songList) {
        this.c = c;
        this.songList = songList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.playlist_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.tvSongName.setText(song.getName());
        holder.tvArtist.setText(song.getArtist());
        holder.ivThumb.setImageResource(R.drawable.ic_music_circle);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    private void showPlayer(int pos) {

        PlaylistActivity pa = (PlaylistActivity) c;
        pa.getSupportFragmentManager().beginTransaction()
            .replace(R.id.playerHolder, PlayerFragment.newInstance(songList, pos))
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSongName;
        TextView tvArtist;
        ImageView ivThumb;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            tvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
            ivThumb = (ImageView) itemView.findViewById(R.id.ivThumb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPlayer(getAdapterPosition());
                }
            });
        }
    }
}