package com.example.computer.diaryapp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Query("SELECT * from note_table LIMIT 1")
    Note[] getAnyNote();

    @Query("SELECT * from note_table ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE from note_table WHERE id = :note_id")
    void deleteNote(long note_id);

    @Query("DELETE from note_table")
    void deleteAllNotes();
}
