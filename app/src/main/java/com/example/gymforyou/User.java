package com.example.gymforyou;

public class User {

    private String username;
    private String pasworrd;

    public User(String username, String pasworrd) {
        this.username = username;
        this.pasworrd = pasworrd;
    }

    public String getUsername() {
        return username;
    }

    public String getPasworrd() {
        return pasworrd;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasworrd(String pasworrd) {
        this.pasworrd = pasworrd;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pasworrd='" + pasworrd + '\'' +
                '}';
    }


}
