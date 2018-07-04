package com.fiuba.proyectosinformaticos.oupa.view;

import com.fiuba.proyectosinformaticos.oupa.model.UserLogged;

public interface LoginView {
    void validateEmail();
    void validatePassword();
    void showProgress(boolean showLoading);
    void onError();
    void onSuccess(UserLogged body, String accessToken);
}
