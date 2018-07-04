package com.fiuba.proyectosinformaticos.oupa.model;

import android.text.TextUtils;
import android.util.Log;

import com.fiuba.proyectosinformaticos.oupa.presenter.LoginPresenter;
import com.fiuba.proyectosinformaticos.oupa.services.UserService;
import com.fiuba.proyectosinformaticos.oupa.view.LoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private UserService userService;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.userService = new UserService();
    }

    @Override
    public void performLogin(String email, String password) {

        boolean abort = false;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            loginView.validateEmail();
            abort = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            loginView.validatePassword();
            abort = true;
        }

        if (!abort) {

            loginView.showProgress(true);
            userService.createUserSession(email, password).enqueue(new Callback<UserSession>() {
                @Override
                public void onResponse(Call<UserSession> call, Response<UserSession> response) {
                    loginView.showProgress(false);
                    if (response.code() > 199 && response.code() < 300) {

                        final String accessToken = response.body().accessToken;
                        Log.i("Session created", response.body().accessToken);

                        userService.getUserLogged(accessToken).enqueue(new Callback<UserLogged>() {

                            @Override
                            public void onResponse(Call<UserLogged> call, Response<UserLogged> response) {
                                if (response.code() > 199 && response.code() < 300) {
                                    loginView.onSuccess(response.body(), accessToken);
                                } else {
                                    Log.e("getUserLogged", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<UserLogged> call, Throwable t) {
                                Log.e("getUserLogged", t.getMessage());
                            }
                        });

                    } else {
                        Log.e("createUserSession", response.message());
                        loginView.onError();
                    }
                }

                @Override
                public void onFailure(Call<UserSession> call, Throwable t) {
                    loginView.showProgress(false);
                    Log.e("createUserSession", t.getMessage());
                }
            });
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
