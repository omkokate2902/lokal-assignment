# Lokal Job Listings App

An Android application that displays job listings fetched from a remote API with bookmarking support, offline storage, and detailed job views.

## Features

✅ Bottom Navigation UI  
✅ Job Listing with Infinite Scroll  
✅ Detailed Job View on Click  
✅ Bookmarking Support  
✅ Offline Access to Bookmarks (using Room DB)  
✅ Loading, Error, and Empty States  

---

## Screens

- **Jobs**: Fetches jobs from API, displays title, location, salary, and phone number in a scrollable list.
- **Bookmarks**: Shows locally saved jobs bookmarked by the user.

---

## Vido

> 📷 Drive Link: https://drive.google.com/file/d/1lJGSBOsqHyCcVFxzbtP68EsRe1BP6cJY/view?usp=sharing

---

## API

- **Source**: [`https://testapi.getlokalapp.com/common/jobs?page=1`](https://testapi.getlokalapp.com/common/jobs?page=1)  
- Supports pagination — the app uses infinite scroll to load more jobs as the user scrolls down.

---

## Architecture

- **Language**: Java
- **UI**: Material Components
- **Database**: Room DB
- **Parsing**: Gson
- **Navigation**: Bottom Navigation View

---

## Functionality

1. **Home Screen (Jobs Tab)**  
   - Uses RecyclerView with infinite scroll to load jobs from the API.
   - Each card shows:
     - Job Title
     - Location
     - Salary
     - Phone Number

2. **Job Details Screen**  
   - Clicking a job card opens a detailed view.
   - All relevant fields like company, experience, openings, views, etc. are shown.
   - Bookmark toggle supported.
   - Call & WhatsApp integration:
     - Phone: Opens dialer.
     - WhatsApp: Opens chat with prefilled message.

3. **Bookmarks Tab**  
   - Displays a list of all bookmarked jobs stored in Room database.
   - Offline accessible.

4. **Bookmark Handling**  
   - Toggling bookmark stores/removes a job from local Room DB.
   - Bookmark icon reflects current state.

5. **State Management**  
   - Shows:
     - Loading spinner while fetching data.
     - Error view if API fails.
     - Empty view if no jobs/bookmarks are available.

---

## Dependencies

```groovy
implementation 'androidx.recyclerview:recyclerview:1.3.1'
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'
implementation 'com.google.code.gson:gson:2.10.1'
implementation 'com.google.android.material:material:1.11.0'
```

---

## Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/omkokate2902/lokal-job-app.git
   ```

2. Open in **Android Studio**.

3. Build & run the app on an emulator or device with internet access.

---

## Folder Structure

```
├── api
│   ├── ApiClient.java
│   ├── ApiService.java
├── db
│   ├── BookmarkDatabase.java
│   ├── BookmarkDao.java
│   └── BookmarkEntity.java
├── model
│   ├── JobItem.java
│   ├── JobResponse.java
│   └── PrimaryDetails.java
├── ui
│   ├── JobDetailsActivity.java
│   ├── JobsFragment.java
│   ├── JobAdapter.java
│   └── BookmarksFragment.java
├── MainActivity.java
```

## Author

**Om Kokate**
