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

    @Query("SELECT * from note_table ORDER BY mTime DESC")
    LiveData<List<Note>> getAllNotes();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    @Query("DELETE from note_table WHERE id = :note_id")
    void deleteNote(long note_id);

    @Query("DELETE from note_table")
    void deleteAllNotes();
}
