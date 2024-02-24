package com.annuaire.softclient;

import com.annuaire.softclient.model.UserSession;

public class HomeController {
    public void initialize() {
        boolean isAdmin = UserSession.getInstance().isAdmin();
    }

}
