package com.fiuba.proyectosinformaticos.oupa;

import com.fiuba.proyectosinformaticos.oupa.model.UserSession;
import com.fiuba.proyectosinformaticos.oupa.services.UserService;
import com.google.firebase.iid.FirebaseInstanceId;


public class UserManager {

    private static UserManager instance;
    private UserService userService;
    private UserSession userSession;

    private UserManager() {
        userService = new UserService();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }

    public void logUser() {
        if (userSession == null) {
            String email = "mau.capo@example.com";
            String password = "12345678";
            String deviceToken = FirebaseInstanceId.getInstance().getToken();
            userService.createUserSession(email, password, deviceToken);
        }
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public String getAuthorizationToken() {
        if (userSession == null) return null;
        return userSession.accessToken;
    }
}
