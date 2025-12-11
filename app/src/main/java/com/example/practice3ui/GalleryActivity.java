package com.example.practice3ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.practice3ui.databinding.ActivityGalleryBinding;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private ActivityGalleryBinding binding;
    private final List<Uri> images = new ArrayList<>();
    private ImageAdapter adapter;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        int inserted = 0;
                        if (data.getClipData() != null) {
                            int n = data.getClipData().getItemCount();
                            for (int i = 0; i < n; i++) {
                                Uri u = data.getClipData().getItemAt(i).getUri();
                                persist(u);
                                images.add(u);
                                UriStorage.addToGallery(GalleryActivity.this, u);
                                inserted++;
                            }
                        } else if (data.getData() != null) {
                            Uri u = data.getData();
                            persist(u);
                            images.add(u);
                            UriStorage.addToGallery(GalleryActivity.this, u);
                            inserted = 1;
                        }
                        if (inserted > 0) adapter.notifyItemRangeInserted(images.size() - inserted, inserted);
                    }
                }
            });

    private void persist(Uri uri) {
        try {
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (Exception ignored) {}
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        images.addAll(UriStorage.getGalleryUris(this));
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new ImageAdapter(this, images,
                pos -> openFull(pos),
                pos -> confirmDelete(pos));

        binding.recycler.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(v -> launchImagePicker());
    }

    private void openFull(int start) {
        Intent i = new Intent(this, FullscreenActivity.class);
        ArrayList<String> payload = new ArrayList<>();
        for (Uri u : images) payload.add(u.toString());
        i.putStringArrayListExtra("uris", payload);
        i.putExtra("start", start);
        startActivity(i);
    }

    private void confirmDelete(int pos) {
        new AlertDialog.Builder(this)
                .setMessage("Видалити з галереї?")
                .setPositiveButton("Так", (d, w) -> {
                    images.remove(pos);
                    UriStorage.saveGalleryUris(this, images);
                    adapter.notifyItemRemoved(pos);
                })
                .setNegativeButton("Hi", null)
                .show();
    }

    private void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        pickImageLauncher.launch(intent);
    }
}