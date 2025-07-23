package com.example.notesfrontend.repo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.notesfrontend.model.AppDatabase;
import com.example.notesfrontend.model.Note;
import com.example.notesfrontend.model.NoteDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// PUBLIC_INTERFACE
public class NoteRepository {
    private final NoteDao noteDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NoteRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        noteDao = db.noteDao();
    }

    // PUBLIC_INTERFACE
    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    // PUBLIC_INTERFACE
    public LiveData<Note> getNoteById(long id) {
        return noteDao.getNoteById(id);
    }

    // PUBLIC_INTERFACE
    public LiveData<List<Note>> searchNotes(String query) {
        return noteDao.searchNotes("%" + query + "%");
    }

    // PUBLIC_INTERFACE
    public void insert(Note note) {
        executorService.execute(() -> noteDao.insert(note));
    }

    // PUBLIC_INTERFACE
    public void update(Note note) {
        executorService.execute(() -> noteDao.update(note));
    }

    // PUBLIC_INTERFACE
    public void delete(Note note) {
        executorService.execute(() -> noteDao.delete(note));
    }
}
