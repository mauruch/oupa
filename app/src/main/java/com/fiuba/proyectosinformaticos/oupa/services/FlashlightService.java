package com.fiuba.proyectosinformaticos.oupa.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;

import android.util.Log;


public class FlashlightService extends Service {
    private Camera camera;
    private boolean isFlashOn;
    Camera.Parameters params;

    public FlashlightService() {
        this.isFlashOn=false;
    }

    @Override
    public void onCreate() {
        getCamera();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isFlashOn) {
            // turn off flash
            turnOffFlash();
            isFlashOn=false;
            //stopSelf();
        } else {
            // turn on flash
            turnOnFlash();
            isFlashOn=true;
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Get the camera
    private void getCamera() {
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
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

            // changing button/switch image
            //toggleButtonImage();
        }

    }


    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
            //toggleButtonImage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
