package com.example.lokalassignment.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BookmarkEntity.class}, version = 1)
public abstract class BookmarkDatabase extends RoomDatabase {

    private static BookmarkDatabase instance;

    public abstract BookmarkDao bookmarkDao();

    public static synchronized BookmarkDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BookmarkDatabase.class, "bookmark_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // For simplicity. Use AsyncTask or Executors in production.
                    .build();
        }
        return instance;
    }
}