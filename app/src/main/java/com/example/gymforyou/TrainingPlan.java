package com.example.gymforyou;

import java.util.ArrayList;

public class TrainingPlan {
    private String nameOfTrainingPlan;
    private String creator;
    private ArrayList<DayOfTraining> dayOfTrainingList;
    private String typeOfTrainingPlan;

    public TrainingPlan(String nameOfTrainingPlan, String creator, ArrayList<DayOfTraining> dayOfTrainingList, String typeOfTrainingPlan) {
        this.nameOfTrainingPlan = nameOfTrainingPlan;
        this.creator = creator;
        this.dayOfTrainingList = dayOfTrainingList;
        this.typeOfTrainingPlan = typeOfTrainingPlan;
    }

    public TrainingPlan() {
    }

    public String getNameOfTrainingPlan() {
        return nameOfTrainingPlan;
    }

    public void setNameOfTrainingPlan(String nameOfTrainingPlan) {
        this.nameOfTrainingPlan = nameOfTrainingPlan;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<DayOfTraining> getDayOfTrainingList() {
        return dayOfTrainingList;
    }

    public void setDayOfTrainingList(ArrayList<DayOfTraining> dayOfTrainingList) {
        this.dayOfTrainingList = dayOfTrainingList;
    }

    public String getTypeOfTrainingPlan() {
        return typeOfTrainingPlan;
    }

    public void setTypeOfTrainingPlan(String typeOfTrainingPlan) {
        this.typeOfTrainingPlan = typeOfTrainingPlan;
    }

    @Override
    public String toString() {
        return "TrainingPlan{" +
                "nameOfTrainingPlan='" + nameOfTrainingPlan + '\'' +
                ", creator='" + creator + '\'' +
                ", dayOfTrainingList=" + dayOfTrainingList +
                '}';
    }
}
