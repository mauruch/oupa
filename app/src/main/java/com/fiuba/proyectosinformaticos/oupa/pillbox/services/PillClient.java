package com.fiuba.proyectosinformaticos.oupa.pillbox.services;

public interface PillClient<T> {

    public abstract void onResponseSuccess(T responseBody);

    public abstract void onResponseError();
}
