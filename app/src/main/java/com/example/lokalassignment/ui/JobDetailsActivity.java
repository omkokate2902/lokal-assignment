package com.example.lokalassignment.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lokalassignment.R;
import com.example.lokalassignment.db.BookmarkDatabase;
import com.example.lokalassignment.db.BookmarkEntity;
import com.example.lokalassignment.model.JobItem;
import com.google.gson.Gson;

public class JobDetailsActivity extends AppCompatActivity {

    private ImageView bookmarkIcon;
    private JobItem job;
    private BookmarkDatabase db;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        db = BookmarkDatabase.getInstance(this);
        job = (JobItem) getIntent().getSerializableExtra("job");

        // Initialize views
        TextView title = findViewById(R.id.details_title);
        TextView company = findViewById(R.id.details_company);
        TextView place = findViewById(R.id.details_place);
        TextView salary = findViewById(R.id.details_salary);
        TextView jobType = findViewById(R.id.details_job_type);
        TextView experience = findViewById(R.id.details_experience);
        TextView qualification = findViewById(R.id.details_qualification);
        TextView category = findViewById(R.id.details_job_category);
        TextView role = findViewById(R.id.details_job_role);
        TextView openings = findViewById(R.id.details_openings);
        TextView views = findViewById(R.id.details_views);
        TextView applications = findViewById(R.id.details_applications);
        TextView premium = findViewById(R.id.details_premium);
        TextView phone = findViewById(R.id.details_phone);
        TextView whatsapp = findViewById(R.id.details_whatsapp);
        TextView otherDetails = findViewById(R.id.details_other_details);
        bookmarkIcon = findViewById(R.id.details_bookmark);

        // Set data
        title.setText(job.title);
        company.setText("🏢 Company: " + safe(job.company_name));
        place.setText("📍 Location: " + safe(job.primary_details != null ? job.primary_details.place : null));
        salary.setText("💰 Salary: " + safe(job.primary_details != null ? job.primary_details.salary : null));
        jobType.setText("🧰 Job Type: " + safe(job.primary_details != null ? job.primary_details.job_type : null));
        experience.setText("📅 Experience: " + safe(job.primary_details != null ? job.primary_details.experience : null));
        qualification.setText("🎓 Qualification: " + safe(job.primary_details != null ? job.primary_details.qualification : null));
        category.setText("📂 Category: " + safe(job.job_category));
        role.setText("👤 Role: " + safe(job.job_role));
        openings.setText("📢 Openings: " + job.openings_count);
        views.setText("👁️ Views: " + job.views);
        applications.setText("📨 Applications: " + job.num_applications);
        premium.setText("⭐ Premium: " + (job.is_premium ? "Yes" : "No"));
        phone.setText("📞 Phone: " + (job.custom_link != null ? job.custom_link.replace("tel:", "") : "N/A"));
        whatsapp.setText("💬 WhatsApp: " + safe(job.whatsapp_no));
        otherDetails.setText("📄 Other Details:\n" + safe(job.other_details));

        // Update bookmark icon state
        updateBookmarkIcon();

        // Handle bookmark click
        bookmarkIcon.setOnClickListener(v -> {
            boolean currentlyBookmarked = db.bookmarkDao().getBookmarkById(job.id) != null;
            if (currentlyBookmarked) {
                db.bookmarkDao().deleteById(job.id);
                Toast.makeText(this, "Removed from bookmarks", Toast.LENGTH_SHORT).show();
            } else {
                db.bookmarkDao().insert(new BookmarkEntity(job.id, gson.toJson(job)));
                Toast.makeText(this, "Added to bookmarks", Toast.LENGTH_SHORT).show();
            }
            updateBookmarkIcon();
        });

        // Handle phone click
        phone.setOnClickListener(v -> {
            String number = job.custom_link != null ? job.custom_link.replace("tel:", "") : null;
            if (number != null && !number.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        // Handle WhatsApp click
        whatsapp.setOnClickListener(v -> {
            if (job.whatsapp_no != null && !job.whatsapp_no.isEmpty()) {
                String message = "Hi, I am interested to apply on the job opening for " +
                        safe(job.title) + " that you posted on the Lokal app";
                String url = "https://wa.me/91" + job.whatsapp_no + "?text=" + Uri.encode(message);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    // Helper to avoid null crashes
    private String safe(String text) {
        return text != null ? text : "N/A";
    }

    // Update bookmark icon based on the bookmark state
    private void updateBookmarkIcon() {
        boolean isBookmarked = db.bookmarkDao().getBookmarkById(job.id) != null;
        bookmarkIcon.setImageResource(isBookmarked ? R.drawable.bookmarked : R.drawable.bookmark);
    }
}