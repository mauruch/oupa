package com.fiuba.proyectosinformaticos.oupa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.fiuba.proyectosinformaticos.oupa.activities.MainActivity;

public class Flashlight {

    private static Camera camera;
    private boolean isFlashOn = false;
    private static Camera.Parameters params;
    private static final int CAMERA_REQUEST = 50;


    private static final Flashlight ourInstance = new Flashlight();

    public static Flashlight getInstance() {
        getCamera();
        return ourInstance;
    }

    private Flashlight() {

    }

    public void toggleFlashlight(ImageView flashlightView, PackageManager packageManager, MainActivity mainActivity) {

        final boolean hasCameraFlash = packageManager.
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        if (hasCameraFlash) {
            if (isEnabled) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                    isFlashOn=false;
                    flashlightView.setImageResource(R.drawable.linterna);
                } else {
                    // turn on flash
                    turnOnFlash();
                    isFlashOn=true;
                    flashlightView.setImageResource(R.drawable.linterna_on);
                }
            } else {
                ActivityCompat.requestPermissions(mainActivity, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
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

                //WTF, si no le pongo esto no funciona a la primera!!
                Thread.sleep(250);

            } catch (RuntimeException e) {
                Log.e("Cant open Camera", e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // Turning On flash
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                Log.e("Flashlight","Camera Not Found");
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

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
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

        }
    }


}
