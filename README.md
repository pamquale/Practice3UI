# Practice3UI - Interactive Media App 🖼️🎵

**Practice3UI** is an Android application designed to demonstrate modern UI building techniques, media handling, and secure file access using Java.

## ✨ Features

* **🏠 Dynamic Home Screen:**
    * Customizable background image via Settings.
    * Background music player (Play/Pause logic) using `MediaPlayer`.
* **🖼️ Gallery (Grid View):**
    * Displays a collection of images using `RecyclerView` with `GridLayoutManager`.
    * **Add Custom Photos:** Uses **SAF (Storage Access Framework)** to pick images from the device storage.
    * **Persistent Access:** Saves image URIs using `SharedPreferences` and requests `persistableUriPermission` to keep access after app restart.
* **⚙️ Settings:**
    * Preview and change the main menu wallpaper.
    * Reset to default background.

## 🛠️ Tech Stack

* **Language:** Java
* **Minimum SDK:** 26 (Android 8.0)
* **Target SDK:** 34 (Android 14)
* **Key Components:**
    * `ViewBinding` - for safer view interaction.
    * `RecyclerView` - for efficient image lists.
    * `SharedPreferences` & `JSON` - for storing data.
    * `ActivityResultLauncher` - for handling system intents (picking files).

## 🚀 Setup & Run

1.  Clone the repository.
2.  Open in **Android Studio**.
3.  Ensure you have a JDK 17 configured.
4.  Run on an emulator or physical device.
