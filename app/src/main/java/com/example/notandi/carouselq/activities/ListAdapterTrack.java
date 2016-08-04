package com.example.notandi.carouselq.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.queueController.QController;
import com.example.notandi.carouselq.queueController.Track;

import java.util.ArrayList;

/**
 * Created by Jósúa on 03-Aug-16.
 */

public class ListAdapterTrack extends BaseAdapter implements ListAdapter {
    private ArrayList<Track> list = new ArrayList<Track>();
    private Context context;
    private QController mQController;

    public ListAdapterTrack(ArrayList<Track> list, Context context) {
        this.list = list;
        this.context = context;
        this.mQController = QController.getInstance();
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
            view = inflater.inflate(R.layout.search_track_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemTextTrack = (TextView)view.findViewById(R.id.track_name);
        listItemTextTrack.setText(list.get(position).getTrackName());

        TextView listItemTextArtist = (TextView)view.findViewById(R.id.band_name);
        listItemTextArtist.setText(list.get(position).getArtistName());

        //Handle buttons and add onClickListeners
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something+
                //TODO kalla a bakenda til ad skra lag i database

                mQController.addSongToQueue(list.get(position));
                mQController.playIfFirst();
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
