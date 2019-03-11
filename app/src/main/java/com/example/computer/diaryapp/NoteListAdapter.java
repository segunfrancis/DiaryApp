package com.example.computer.diaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final LayoutInflater mInflater;
    private List<Note> mNotes; // Cached copy of notes

    public NoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.note.setText(current.getNote());
            holder.category.setText(current.getCategory());
            holder.time.setText(current.getTime());
            holder.date.setText(current.getDate());
        } else {
            holder.note.setText("No Notes");
        }
    }

    void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else
            return 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView note, category, date, time;

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note_textview);
            category = itemView.findViewById(R.id.category_textview);
            date = itemView.findViewById(R.id.date_textview);
            time = itemView.findViewById(R.id.time_textview);
        }
    }
}
