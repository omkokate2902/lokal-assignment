package com.example.lokalassignment.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class BookmarkEntity {

    @PrimaryKey
    public int id;

    public String jobJson; // Store full job object as JSON string

    public BookmarkEntity(int id, String jobJson) {
        this.id = id;
        this.jobJson = jobJson;
    }
}