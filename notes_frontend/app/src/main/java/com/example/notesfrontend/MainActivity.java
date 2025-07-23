package com.example.notesfrontend;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notesfrontend.model.Note;
import com.example.notesfrontend.ui.NoteDetailFragment;
import com.example.notesfrontend.ui.NoteEditFragment;
import com.example.notesfrontend.ui.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NoteListFragment())
                    .commit();
        }
    }

    // PUBLIC_INTERFACE
    @Override
    public void navigateToAddNote() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NoteEditFragment())
                .addToBackStack(null)
                .commit();
    }

    // PUBLIC_INTERFACE
    @Override
    public void navigateToNoteDetail(Note note) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, NoteDetailFragment.newInstance(note.id))
                .addToBackStack(null)
                .commit();
    }
}
