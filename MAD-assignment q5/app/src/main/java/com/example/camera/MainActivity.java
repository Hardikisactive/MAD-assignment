package com.example.camera;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 100;
    private Uri photoUri;
    private String currentPhotoPath;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openCameraButton = findViewById(R.id.openCameraButton);
        imageView = findViewById(R.id.imageView);

        openCameraButton.setOnClickListener(v -> openCamera());
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, CAMERA_REQUEST);
                Toast.makeText(this, "Opening camera...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";
        File storageDir = new File(android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_DCIM), "Camera");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File imageFile = new File(storageDir, fileName);
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath));
            Toast.makeText(this, "Image saved to: " + currentPhotoPath, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Image capture cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
