package com.example.lokalassignment.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PrimaryDetails implements Serializable {

    @SerializedName("Place")
    public String place;

    @SerializedName("Salary")
    public String salary;

    @SerializedName("Job_Type")
    public String job_type;

    @SerializedName("Experience")
    public String experience;

    @SerializedName("Fees_Charged")
    public String fees_charged;

    @SerializedName("Qualification")
    public String qualification;
}