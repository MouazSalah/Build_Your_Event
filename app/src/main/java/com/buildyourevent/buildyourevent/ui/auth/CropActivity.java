package com.buildyourevent.buildyourevent.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class CropActivity extends AppCompatActivity
{
    public static Bitmap croppedImage;
    private CropImageView cropImageView;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        initActivity();
    }

    private void initActivity()
    {
        // Set URI image to display
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        cropImageView.setImageBitmap(RegisterActivity.bitmapPhoto);

        // Rotate image the cropped image using function and angle
        // cropImageView.rotateImage(angle);
        // For ex., cropImageView.rotateImage(-90);

        Button mFab =  findViewById(R.id.nextStep);
        mFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (view.getId() == R.id.nextStep)
                {
                    cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener()
                    {
                        @Override
                        public void onCropImageComplete(CropImageView view, CropImageView.CropResult result)
                        {
                            croppedImage = result.getBitmap();
                            Codes.CROP_PHOTO = croppedImage;
                            Intent intent = new Intent();
                            setResult(intent);
                            finish();
                        }
                    });
                    cropImageView.getCroppedImageAsync();
                }
            }
        });
    }

    private void setResult(Intent intent)
    {

    }
}
