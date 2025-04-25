package com.example.lokalassignment.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class JobItem implements Serializable {
    public int id;
    public String title;
    public int type;

    @SerializedName("primary_details")
    public PrimaryDetails primary_details;

    public int salary_max;
    public int salary_min;
    public String content;
    public String company_name;
    public String button_text;
    public String custom_link;
    public String whatsapp_no;

    public boolean is_bookmarked;
    public boolean is_applied;
    public boolean is_owner;
    public boolean is_premium;

    public int openings_count;
    public String job_role;
    public String job_category;
    public String other_details;
    public int views;
    public int num_applications;
    public String updated_on;
    public String created_on;
}