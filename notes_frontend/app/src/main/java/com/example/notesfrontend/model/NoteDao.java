package com.example.notesfrontend.model;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

// PUBLIC_INTERFACE
@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<Note> getNoteById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

    @Update
    int update(Note note);

    @Delete
    int delete(Note note);

    @Query("SELECT * FROM notes WHERE title LIKE :query OR content LIKE :query ORDER BY updatedAt DESC")
    LiveData<List<Note>> searchNotes(String query);
}
