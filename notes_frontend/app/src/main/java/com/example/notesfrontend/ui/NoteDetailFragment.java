package com.example.notesfrontend.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.notesfrontend.R;
import com.example.notesfrontend.model.Note;
import com.example.notesfrontend.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// PUBLIC_INTERFACE
public class NoteDetailFragment extends Fragment {

    private static final String ARG_NOTE_ID = "note_id";
    private EditText editTitle, editContent;
    private NoteViewModel noteViewModel;
    private Note note;

    public static NoteDetailFragment newInstance(long noteId) {
        NoteDetailFragment f = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_NOTE_ID, noteId);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_detail, container, false);
        editTitle = v.findViewById(R.id.edit_title);
        editContent = v.findViewById(R.id.edit_content);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        long noteId = getArguments() != null ? getArguments().getLong(ARG_NOTE_ID) : 0;

        noteViewModel.getNoteById(noteId).observe(getViewLifecycleOwner(), n -> {
            if (n != null) {
                note = n;
                editTitle.setText(note.title);
                editContent.setText(note.content);
            }
        });

        FloatingActionButton fabSave = v.findViewById(R.id.fab_save_note);
        fabSave.setOnClickListener(vv -> saveNote());

        FloatingActionButton fabDelete = v.findViewById(R.id.fab_delete_note);
        fabDelete.setOnClickListener(vv -> deleteNote());

        // Back pressed: save changes if not blank
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    saveNote();
                }
            });

        return v;
    }

    private void saveNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "Empty note discarded", Toast.LENGTH_SHORT).show();
            deleteNote();
            return;
        }
        note.title = title;
        note.content = content;
        note.updatedAt = System.currentTimeMillis();
        noteViewModel.update(note);
        Toast.makeText(getContext(), "Note updated", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void deleteNote() {
        if (note != null) {
            noteViewModel.delete(note);
            Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_SHORT).show();
        }
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
