package com.example.gymforyou;

public class Admin extends User {

    private int adminLevel;

    public Admin(String username, String password, String email, String phoneNumber, int adminLevel) {
        super(username, password, email, phoneNumber);
        this.adminLevel = adminLevel;
    }

    public Admin() {
    }

    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminLevel=" + adminLevel +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
