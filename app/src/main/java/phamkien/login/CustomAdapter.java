package phamkien.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hannibal Lecter on 02/19/19.
 */

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Song> playlist = new ArrayList<>();

    public CustomAdapter(Context ctx, ArrayList<Song> playlist) {
        this.context = ctx;
        this.playlist = playlist;
        inflater = (LayoutInflater.from(ctx));
    }

    @Override
    public int getCount() {
        return playlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.playlist, null);
        TextView tvSong = (TextView) view.findViewById(R.id.tvSong);
        TextView tvSinger = (TextView) view.findViewById(R.id.tvSinger);
        tvSong.setText(playlist.get(i).getName());
        tvSinger.setText(playlist.get(i).getSinger());
        return view;
    }
}