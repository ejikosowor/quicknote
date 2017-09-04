package com.eyeq.quicknotes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zulu on 9/4/17.
 */

public class Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Note> noteItems;

    public Adapter(Activity activity, List<Note> noteItems) {
        this.activity = activity;
        this.noteItems = noteItems;
    }

    @Override
    public int getCount() {
        return noteItems.size();
    }

    @Override
    public Object getItem(int position) {
        return noteItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView date = (TextView) convertView.findViewById(R.id.year);

        Note m = noteItems.get(position);
        title.setText(m.getTitle());
        date.setText(m.getDate());

        return convertView;
    }
}
