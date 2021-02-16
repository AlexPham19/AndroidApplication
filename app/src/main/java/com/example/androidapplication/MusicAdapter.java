package com.example.androidapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.listviewintermediate.R;

import java.util.List;

/** Cái này là adapter nâng cao **/
public class MusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Music> musicList;

    public MusicAdapter(Context context, int layout, List<Music> musicList) {
        this.context = context;
        this.layout = layout;
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size(); // Nhớ thay đổi cái củ lol này đừng để = 0
    }

    @Override
    public Object getItem(int position) {
        return null; // ko dùng
    }

    @Override
    public long getItemId(int position) {
        return 0;
    } // không dùng

    private class ViewHolder{
        ImageView imageView;
        TextView name, artist;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            // ánh xạ
            holder.name = convertView.findViewById(R.id.Name);
            holder.artist = convertView.findViewById(R.id.Artist_now_playing);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // gán giá trị
        Music music = musicList.get(position);
        holder.name.setText(music.getName());
        holder.artist.setText(music.getArtist());
        holder.imageView.setImageResource(music.getImage());
        return convertView;
    }
}
