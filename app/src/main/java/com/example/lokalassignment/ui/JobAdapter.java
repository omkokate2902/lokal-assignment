package com.example.lokalassignment.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lokalassignment.R;
import com.example.lokalassignment.db.BookmarkDatabase;
import com.example.lokalassignment.db.BookmarkEntity;
import com.example.lokalassignment.model.JobItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private final List<JobItem> jobList = new ArrayList<>();
    private final Context context;
    private final BookmarkDatabase db;
    private final Gson gson = new Gson();

    public JobAdapter(Context context) {
        this.context = context;
        this.db = BookmarkDatabase.getInstance(context);
    }

    public void addJobs(List<JobItem> newJobs) {
        int start = jobList.size();
        jobList.addAll(newJobs);
        notifyItemRangeInserted(start, newJobs.size());
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobItem job = jobList.get(position);

        String title = job.title != null ? job.title : "Not Available";
        String displayPlace = (job.primary_details != null && job.primary_details.place != null) ?
                job.primary_details.place : "Not Available";  // Access primary_details.place
        String displaySalary = (job.primary_details != null && job.primary_details.salary != null) ?
                job.primary_details.salary : "Not Available";
        String displayPhone = job.custom_link != null ? job.custom_link.replace("tel:", "") : "Not Available";

        holder.title.setText(title);
        holder.place.setText(displayPlace);  // Use primary_details.place here
        holder.salary.setText(displaySalary);
        holder.phone.setText("📞 " + displayPhone);

        // Update bookmark icon based on the current state
        boolean isBookmarked = db.bookmarkDao().getBookmarkById(job.id) != null;
        holder.bookmarkIcon.setImageResource(isBookmarked ? R.drawable.bookmarked : R.drawable.bookmark);

        // Bookmark button click listener
        holder.bookmarkIcon.setOnClickListener(v -> {
            boolean currentlyBookmarked = db.bookmarkDao().getBookmarkById(job.id) != null;
            if (currentlyBookmarked) {
                // Remove from bookmarks
                db.bookmarkDao().deleteById(job.id);
                holder.bookmarkIcon.setImageResource(R.drawable.bookmark);  // Update icon to unbookmarked
            } else {
                // Add to bookmarks
                db.bookmarkDao().insert(new BookmarkEntity(job.id, gson.toJson(job)));
                holder.bookmarkIcon.setImageResource(R.drawable.bookmarked);  // Update icon to bookmarked
            }
        });

        if (job.is_premium) {
            holder.premiumTag.setVisibility(View.VISIBLE);
        } else {
            holder.premiumTag.setVisibility(View.GONE);
        }

        holder.views.setText(String.valueOf(job.views));

        // Click to open job details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), JobDetailsActivity.class);
            intent.putExtra("job", job);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title, place, salary, phone, premiumTag, views;
        ImageView bookmarkIcon;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.job_title);
            place = itemView.findViewById(R.id.job_place);
            salary = itemView.findViewById(R.id.job_salary);
            phone = itemView.findViewById(R.id.job_phone);
            premiumTag = itemView.findViewById(R.id.premium_tag);
            views = itemView.findViewById(R.id.views);
            bookmarkIcon = itemView.findViewById(R.id.bookmark_icon);
        }
    }
}