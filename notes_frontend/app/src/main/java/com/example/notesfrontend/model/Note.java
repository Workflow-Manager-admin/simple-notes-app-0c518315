package com.example.notesfrontend.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// PUBLIC_INTERFACE
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String content;
    public long createdAt;
    public long updatedAt;

    public Note(String title, String content, long createdAt, long updatedAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
