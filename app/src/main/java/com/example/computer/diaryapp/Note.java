package com.example.computer.diaryapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "note")
    private String mNote;

    @ColumnInfo(name = "category")
    private String mCategory;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "time")
    private String mTime;

    public Note(int id, @NonNull String note, String category, String date, String time) {
        this.id = id;
        this.mNote = note;
        this.mCategory = category;
        this.mDate = date;
        this.mTime = time;
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