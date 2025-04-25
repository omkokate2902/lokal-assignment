package com.example.lokalassignment.db;

import androidx.room.*;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkEntity bookmark);

    @Delete
    void delete(BookmarkEntity bookmark);

    @Query("DELETE FROM bookmarks WHERE id = :jobId")
    void deleteById(int jobId);

    @Query("SELECT * FROM bookmarks")
    List<BookmarkEntity> getAllBookmarks();

    @Query("SELECT * FROM bookmarks WHERE id = :jobId")
    BookmarkEntity getBookmarkById(int jobId);
}