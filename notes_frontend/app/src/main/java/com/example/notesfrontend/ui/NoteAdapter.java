package com.example.notesfrontend.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesfrontend.R;
import com.example.notesfrontend.model.Note;

import java.util.ArrayList;
import java.util.List;

// PUBLIC_INTERFACE
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    private List<Note> notes = new ArrayList<>();
    private final OnNoteClickListener listener;

    public NoteAdapter(OnNoteClickListener listener) {
        this.listener = listener;
    }

    // PUBLIC_INTERFACE
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textTitle.setText(note.title);
        holder.textPreview.setText(note.content.length() > 45 ? note.content.substring(0, 45) + "..." : note.content);
        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textPreview;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.note_title);
            textPreview = itemView.findViewById(R.id.note_preview);
        }
    }
}
