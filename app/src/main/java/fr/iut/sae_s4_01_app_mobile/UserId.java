package fr.iut.sae_s4_01_app_mobile;

import android.app.Application;

public class UserId extends Application {
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
