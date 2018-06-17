package com.fiuba.proyectosinformaticos.oupa;

import android.hardware.Camera;
import android.util.Log;
import android.widget.ImageView;

public class Flashlight {

    private static Camera camera;
    private boolean isFlashOn = false;
    static Camera.Parameters params;

    private static final Flashlight ourInstance = new Flashlight();

    public static Flashlight getInstance() {
        getCamera();
        return ourInstance;
    }

    private Flashlight() {

    }

    public void toggleFlashlight(ImageView flashlightView) {

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
    }

    // Get the camera
    private static void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();

                //WTF, si no le pongo esto no funciona a la primera!!
                Thread.sleep(200);

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
