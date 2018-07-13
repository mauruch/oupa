package com.fiuba.proyectosinformaticos.oupa.contacts.services;

import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.contacts.Contact;
import com.fiuba.proyectosinformaticos.oupa.contacts.NewContact;
import com.fiuba.proyectosinformaticos.oupa.contacts.NewContact4;
import com.fiuba.proyectosinformaticos.oupa.networking.ApiClient;
import com.fiuba.proyectosinformaticos.oupa.networking.OupaApi;
import com.fiuba.proyectosinformaticos.oupa.pillbox.NewPillStep4;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactService {
    private OupaApi oupaApi;

    public ContactService() {
        oupaApi = ApiClient.getInstance().getOupaClient();
    }

    public Call<ContactResponse> createContact(String accessToken, String s, ContactSerialized contactSerialized) {
        return oupaApi.createContact(accessToken, s, contactSerialized);
    }

    public void createNewContact(final Contact contact, final NewContact4 delegate) {

        ContactSerialized contactSerialized = new ContactSerialized();
        contactSerialized.contact = new ContactSerialized.ContactS();
        contactSerialized.contact.name = contact.name;
        contactSerialized.contact.phoneNumber = contact.phoneNumber;
        contactSerialized.contact.picture = contact.picture;

        String accessToken = new UserSessionManager(delegate.getApplicationContext()).getAuthorizationToken();

        createContact(accessToken,"application/json",contactSerialized).enqueue(new Callback<ContactResponse>() {

            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                if (response.code() > 199 && response.code() < 300) {
                    Log.i("CONTACTSERVICE", "NEW CONTACT CREATED!!!");
                    delegate.onResponseSuccess(response.body());
                } else {
                    Log.e("CONTACTSERVICE", response.body().toString());
                    delegate.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                Log.e("CONTACTSERVICE", t.getMessage());
                delegate.onResponseError();
            }
        });
    }

    public void getContacts(final ContactClient delegate) {
        String accessToken = new UserSessionManager(delegate.getApplicationContext()).getAuthorizationToken();
        oupaApi.getContacts(accessToken).enqueue(new Callback<ArrayList<ContactResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ContactResponse>> call, Response<ArrayList<ContactResponse>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("CONTACTSERVICE", response.body().toString());
                        delegate.onResponseSuccess(response.body());
                    }else {
                        Log.i("CONTACTSERVICE", "NO RESPONSE");
                        delegate.onResponseError();
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("CONTACTSERVICE", response.body().toString());
                    }else {
                        Log.e("CONTACTSERVICE", "NO RESPONSE");
                    }
                    delegate.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContactResponse>> call, Throwable t) {
                delegate.onResponseError();
                Log.e("CONTACTSERVICE", t.getMessage());
            }
        });
    }

//    public void updatePillDrinked(Context applicationContext, final Pill pill){
//        PillTakenSerialized pillTakenSerialized = new PillTakenSerialized();
//        pillTakenSerialized.personal_medicine_reminder = new PillTakenSerialized.Personal_medicine_reminder();
//        pillTakenSerialized.personal_medicine_reminder.taken = pill.drinked;
//
//        String accessToken = new UserSessionManager(applicationContext).getAuthorizationToken();
//        oupaApi.drinkedPill(accessToken, pill.id, pillTakenSerialized).enqueue(new Callback<PillResponse>() {
//
//            @Override
//            public void onResponse(Call<PillResponse> call, Response<PillResponse> response) {
//                if (response.code() > 199 && response.code() < 300) {
//                    Log.i("PILLSERVICE", "PILL " +pill.id+" UPDATED!!!");
//                } else {
//                    Log.e("PILLSERVICE", response.body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PillResponse> call, Throwable t) {
//                Log.e("PILLSERVICE", t.getMessage());
//            }
//        });
//    }
}
