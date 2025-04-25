package com.example.lokalassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lokalassignment.R;
import com.example.lokalassignment.db.BookmarkDatabase;
import com.example.lokalassignment.db.BookmarkEntity;
import com.example.lokalassignment.model.JobItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment {

    private JobAdapter adapter;
    private BookmarkDatabase db;
    private final Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = BookmarkDatabase.getInstance(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new JobAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        loadBookmarks();
    }

    private void loadBookmarks() {
        List<BookmarkEntity> bookmarkEntities = db.bookmarkDao().getAllBookmarks();
        List<JobItem> jobs = new ArrayList<>();
        for (BookmarkEntity entity : bookmarkEntities) {
            JobItem job = gson.fromJson(entity.jobJson, JobItem.class);
            jobs.add(job);
        }
        adapter.addJobs(jobs);
    }
}