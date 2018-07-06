package com.fiuba.proyectosinformaticos.oupa.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiuba.proyectosinformaticos.oupa.R;
import com.fiuba.proyectosinformaticos.oupa.UserSessionManager;
import com.fiuba.proyectosinformaticos.oupa.model.LoginPresenterImpl;
import com.fiuba.proyectosinformaticos.oupa.model.UserLogged;
import com.fiuba.proyectosinformaticos.oupa.presenter.LoginPresenter;
import com.fiuba.proyectosinformaticos.oupa.view.LoginView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private UserSessionManager userSessionManager;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSessionManager = new UserSessionManager(this);
        loginPresenter = new LoginPresenterImpl(LoginActivity.this);

        checkUserLogged();
        getSupportActionBar().hide();

        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        LinearLayout mEmailSignIn = findViewById(R.id.email_sign_in);
        mEmailSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Drawable background = findViewById(R.id.login_container).getBackground();
        background.setAlpha(120);
    }

    private void checkUserLogged() {
        if (userSessionManager.isUserLoggedIn()) {
            Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        loginPresenter.performLogin(email, password);
    }

    @Override
    public void showProgress(final boolean show) {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void validateEmail() {
        mEmailView.setError(getString(R.string.error_invalid_email));
        mEmailView.requestFocus();
    }

    @Override
    public void validatePassword() {
        mPasswordView.setError(getString(R.string.error_invalid_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void onError() {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void onSuccess(UserLogged userLogged, String accessToken) {
        Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);

        UserSessionManager userSession = new UserSessionManager(this.getApplicationContext());
        userSession.saveUserLogged(userLogged, accessToken);

        String welcome = String.format("Bienvenido %s %s!", userLogged.firstName, userLogged.lastName);

        mainActivityIntent.putExtra("WELCOME", welcome);
        startActivity(mainActivityIntent);
        finish();
    }

}

