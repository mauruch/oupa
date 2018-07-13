package com.fiuba.proyectosinformaticos.oupa.contacts.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactResponse {

    @Expose
    @SerializedName("name")
    public String name;

    @SerializedName("phone_number")
    public String phoneNumber;

    @SerializedName("picture")
    public String picture;
}
