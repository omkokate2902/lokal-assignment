package com.example.lokalassignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lokalassignment.ui.BookmarksFragment;
import com.example.lokalassignment.ui.JobsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            // Check internet connection
            if (!isNetworkConnected()) {
                selectedFragment = new BookmarksFragment();  // Load Bookmarks if no internet
            } else {
                if (itemId == R.id.nav_jobs) {
                    selectedFragment = new JobsFragment();
                } else if (itemId == R.id.nav_bookmarks) {
                    selectedFragment = new BookmarksFragment();
                }
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Set default selected item
        if (savedInstanceState == null) {
            // Check if there's an internet connection
            if (!isNetworkConnected()) {
                bottomNav.setSelectedItemId(R.id.nav_bookmarks); // Set Bookmarks as the default if no internet
            } else {
                bottomNav.setSelectedItemId(R.id.nav_jobs); // Default to Jobs if there's internet
            }
        }
    }

    // Method to check internet connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }
}