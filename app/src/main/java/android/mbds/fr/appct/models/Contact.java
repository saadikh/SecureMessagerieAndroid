package android.mbds.fr.appct.models;

import java.io.Serializable;

public class Contact implements Serializable {
    String username;

    public Contact(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

