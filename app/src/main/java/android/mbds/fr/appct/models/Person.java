package android.mbds.fr.appct.models;

import java.io.Serializable;

public class Person implements Serializable {

    private String username;
    private String password;

    public Person(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Person(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String nom) {
        this.username = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }
}
