package com.example.lokalassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lokalassignment.R;
import com.example.lokalassignment.api.ApiClient;
import com.example.lokalassignment.api.ApiService;
import com.example.lokalassignment.model.JobItem;
import com.example.lokalassignment.model.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsFragment extends Fragment {

    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean isLoading = false;
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        recyclerView = view.findViewById(R.id.recycler_jobs);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new JobAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        loadJobs(currentPage);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (!isLoading && layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - 5) {
                    isLoading = true;
                    loadJobs(++currentPage);
                }
            }
        });

        return view;
    }

    private void loadJobs(int page) {
        ApiService api = ApiClient.getRetrofit().create(ApiService.class);
        api.getJobs(page).enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<JobItem> jobs = response.body().results;
                    adapter.addJobs(jobs);
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load jobs", Toast.LENGTH_SHORT).show();
                isLoading = false;
            }
        });
    }
}