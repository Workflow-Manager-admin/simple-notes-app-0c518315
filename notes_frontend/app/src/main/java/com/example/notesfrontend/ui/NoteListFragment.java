package com.example.notesfrontend.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesfrontend.R;
import com.example.notesfrontend.model.Note;
import com.example.notesfrontend.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

// PUBLIC_INTERFACE
public class NoteListFragment extends Fragment {

    private NoteAdapter adapter;
    private NoteViewModel noteViewModel;
    private EditText searchInput;

    // PUBLIC_INTERFACE
    public interface NavigationHost {
        void navigateToAddNote();
        void navigateToNoteDetail(Note note);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        adapter = new NoteAdapter(note -> {
            if (getActivity() instanceof NavigationHost) {
                ((NavigationHost) getActivity()).navigateToNoteDetail(note);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> adapter.setNotes(notes));

        searchInput = view.findViewById(R.id.edit_search);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteViewModel.searchNotes(s.toString());
                noteViewModel.getSearchResults().observe(getViewLifecycleOwner(), notes -> adapter.setNotes(notes));
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v -> {
            if (getActivity() instanceof NavigationHost) {
                ((NavigationHost) getActivity()).navigateToAddNote();
            }
        });

        return view;
    }
}
