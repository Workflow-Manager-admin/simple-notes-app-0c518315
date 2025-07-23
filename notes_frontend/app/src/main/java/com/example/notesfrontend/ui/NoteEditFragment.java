package com.example.notesfrontend.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.notesfrontend.R;
import com.example.notesfrontend.model.Note;
import com.example.notesfrontend.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// PUBLIC_INTERFACE
public class NoteEditFragment extends Fragment {

    private EditText editTitle, editContent;
    private NoteViewModel noteViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_edit, container, false);
        editTitle = v.findViewById(R.id.edit_title);
        editContent = v.findViewById(R.id.edit_content);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        FloatingActionButton fabSave = v.findViewById(R.id.fab_save_note);
        fabSave.setOnClickListener(view -> saveNote());

        return v;
    }

    private void saveNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "Cannot save blank note", Toast.LENGTH_SHORT).show();
            return;
        }
        long now = System.currentTimeMillis();
        Note newNote = new Note(title, content, now, now);
        noteViewModel.insert(newNote);
        Toast.makeText(getContext(), "Note saved", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
