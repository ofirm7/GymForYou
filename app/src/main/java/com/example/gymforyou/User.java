package com.example.gymforyou;

import java.util.ArrayList;

public class User {

    protected String username;
    protected String password;
    protected String email;
    protected String phoneNumber;
    protected ArrayList<TrainingPlan> trainingPlansList;

    public User(String username, String password, String email, String phoneNumber, ArrayList<TrainingPlan> trainingPlansList) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.trainingPlansList = trainingPlansList;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<TrainingPlan> getTrainingPlansList() {
        return trainingPlansList;
    }

    public void setTrainingPlansList(ArrayList<TrainingPlan> trainingPlansList) {
        this.trainingPlansList = trainingPlansList;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
