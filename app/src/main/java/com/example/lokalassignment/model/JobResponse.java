package com.example.lokalassignment.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class JobResponse {
    @SerializedName("results")
    public List<JobItem> results;
}