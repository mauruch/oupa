package com.fiuba.proyectosinformaticos.oupa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.activities.MainActivity;

import java.io.IOException;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

public class Flashlight {

    private static Camera camera;
    private boolean isFlashOn = false;
    private static Camera.Parameters params;
    private static final int CAMERA_REQUEST = 50;
    private ImageView flashlightView;


    private static final Flashlight ourInstance = new Flashlight();

    public static Flashlight getInstance() {
        getCamera();
        return ourInstance;
    }

    private Flashlight() {}

    public void toggleFlashlight(MainActivity mainActivity) {

        flashlightView = mainActivity.findViewById(R.id.flashlight_layout_image);

        final boolean hasCameraFlash = mainActivity.getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        if (hasCameraFlash) {
            if (isEnabled) {
                if (isFlashOn) {
                    turnOffFlash();
                } else {
                    turnOnFlash();
                }
            } else {
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        } else {
            Toast.makeText(mainActivity, "Tu dispositivo no dispone de linterna",
                    Toast.LENGTH_SHORT).show();
        }
    }


    // Get the camera
    private static void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Cant open Camera", e.getMessage());
            }
        }
    }


    // Turning On flash
    private void turnOnFlash() {
        if (!isFlashOn) {
            //camera = Camera.open();
            if (camera == null || params == null) {
                Log.e("Flashlight","Camera Not Found");
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(FLASH_MODE_TORCH);
            camera.setParameters(params);
            try {
                camera.setPreviewTexture(new SurfaceTexture(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
            isFlashOn = true;

            flashlightView.setImageResource(R.drawable.linterna_on);
        }
    }

    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                Log.e("Flashlight","Camera Not Found");
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.stopPreview();
            camera.release();
            camera = null;
            isFlashOn = false;

            flashlightView.setImageResource(R.drawable.linterna);
        }
    }
}
