package com.example.computer.diaryapp;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Query("DELETE FROM note_table WHERE id = :note_id")
    void deleteNote(long note_id);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * from note_table ORDER BY time DESC")
    List<Note> getAllNotes();
}
