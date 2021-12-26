package com.example.gymforyou;

import java.util.ArrayList;

public class DayOfTraining{
    private ArrayList<Muscle> musclesList;
    private String nameOfDay;

    public DayOfTraining(ArrayList<Muscle> musclesList, String nameOfDay) {
        this.musclesList = musclesList;
        this.nameOfDay = nameOfDay;
    }

    public DayOfTraining() {
    }

    public ArrayList<Muscle> getMusclesList() {
        return musclesList;
    }

    public void setMusclesList(ArrayList<Muscle> musclesList) {
        this.musclesList = musclesList;
    }

    public String getNameOfDay() {
        return nameOfDay;
    }

    public void setNameOfDay(String nameOfDay) {
        this.nameOfDay = nameOfDay;
    }

    @Override
    public String toString() {
        return "DayOfTraining{" +
                "musclesList=" + musclesList +
                ", nameOfDay='" + nameOfDay + '\'' +
                '}';
    }
}
