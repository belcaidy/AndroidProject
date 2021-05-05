package com.example.androidproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(@NonNull Context context, @NonNull List<Note> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newItem = convertView;
        if (newItem == null){
            Log.w(NoteAdapter.class.getSimpleName(),"Creation item "+i);
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            newItem = li.inflate(R.layout.note_item, parent, false);
    }else
            Log.w(NoteAdapter.class.getSimpleName(),"Recyclage pour creer item "+i);
        ImageView icon=newItem.findViewById(R.id.imgIcon);
        TextView t1=newItem.findViewById(R.id.txtMat);
        TextView t2=newItem.findViewById(R.id.txtScore);
        t1.setText(this.getItem(i).getLabel());
        t2.setText(this.getItem(i).getScore()+"");
        if (getItem(i).getScore()<10)
            icon.setImageResource(R.drawable.ic_dislike);
        else
            icon.setImageResource(R.drawable.ic_like);
        return newItem;
    }
}
