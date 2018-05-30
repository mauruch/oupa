package com.fiuba.proyectosinformaticos.oupa.services;

import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;

public class SOSAlertService {
    private OupaApi oupaApi;

    public SOSAlertService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public void sendSOSAlert() {
        oupaApi.createSOSAlert();
    }
}
