package com.example.computer.diaryapp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Query("DELETE from note_table WHERE id = :note_id")
    void deleteNote(long note_id);

    @Query("DELETE from note_table")
    void deleteAllNotes();

    @Query("SELECT * from note_table ORDER BY time DESC")
    LiveData<List<Note>> getAllNotes();
}
