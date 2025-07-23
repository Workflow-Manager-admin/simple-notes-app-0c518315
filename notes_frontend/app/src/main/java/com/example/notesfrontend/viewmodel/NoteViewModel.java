package com.example.notesfrontend.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.notesfrontend.model.Note;
import com.example.notesfrontend.repo.NoteRepository;

import java.util.List;

// PUBLIC_INTERFACE
public class NoteViewModel extends AndroidViewModel {
    private final NoteRepository repository;
    private final LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> searchResults;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        searchResults = allNotes;
    }

    // PUBLIC_INTERFACE
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    // PUBLIC_INTERFACE
    public LiveData<Note> getNoteById(long id) {
        return repository.getNoteById(id);
    }

    // PUBLIC_INTERFACE
    public LiveData<List<Note>> getSearchResults() {
        return searchResults;
    }

    // PUBLIC_INTERFACE
    public void searchNotes(String query) {
        if (query == null || query.isEmpty()) {
            searchResults = allNotes;
        } else {
            searchResults = repository.searchNotes(query);
        }
    }

    // PUBLIC_INTERFACE
    public void insert(Note note) {
        repository.insert(note);
    }

    // PUBLIC_INTERFACE
    public void update(Note note) {
        repository.update(note);
    }

    // PUBLIC_INTERFACE
    public void delete(Note note) {
        repository.delete(note);
    }
}
