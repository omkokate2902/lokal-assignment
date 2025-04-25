package com.example.lokalassignment.api;

import com.example.lokalassignment.model.JobResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("common/jobs") // Correct endpoint
    Call<JobResponse> getJobs(@Query("page") int page);  // Pass page as query param
}