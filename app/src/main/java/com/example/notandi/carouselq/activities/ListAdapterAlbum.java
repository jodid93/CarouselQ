package com.example.notandi.carouselq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.queueController.Album;
import com.example.notandi.carouselq.queueController.Track;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Jósúa on 03-Aug-16.
 */

public class ListAdapterAlbum extends BaseAdapter implements ListAdapter {
    private ArrayList<Album> list = new ArrayList<Album>();
    private Context context;



    public ListAdapterAlbum(ArrayList<Album> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_album_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemTextTrack = (TextView)view.findViewById(R.id.album_name);
        listItemTextTrack.setText(list.get(position).getName());


        //Handle buttons and add onClickListeners
        Button openBtn = (Button)view.findViewById(R.id.open_tracks);

        openBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something+

                Intent i = SearchActivityTrack.newIntent(context);
                i.putExtra("query",list.get(position).getLinkToTracks());
                context.startActivity(i);

                notifyDataSetChanged();
            }
        });

        return view;
    }
}
