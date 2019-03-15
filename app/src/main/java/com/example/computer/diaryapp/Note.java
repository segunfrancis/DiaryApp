package com.example.computer.diaryapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mNote;
    private String mCategory;
    private String mDate;
    private String mTime;

    public Note(int id, String note, String category, String date, String time) {
        this.id = id;
        this.mNote = note;
        this.mCategory = category;
        this.mDate = date;
        this.mTime = time;
    }

    @Ignore
    public Note(String mNote, String mCategory, String mDate, String mTime) {
        this.mNote = mNote;
        this.mCategory = mCategory;
        this.mDate = mDate;
        this.mTime = mTime;
    }

    public int getId() {
        return this.id;
    }

    public String getNote() {
        return mNote;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }
}