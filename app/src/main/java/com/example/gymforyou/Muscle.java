package com.example.gymforyou;

import java.util.ArrayList;

public class Muscle {
    private String name;
    private String creator;
    private String description;
    private ArrayList<Exercise> exercisesList;

    public Muscle() {
    }

    public Muscle(String name, String creator, String description, ArrayList<Exercise> exercisesList) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.exercisesList = exercisesList;
    }

    public Muscle(String name, String creator, String description) {
        this.name = name;
        this.creator = creator;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Exercise> getExercisesList() {
        return exercisesList;
    }

    public void setExercisesList(ArrayList<Exercise> exercisesList) {
        this.exercisesList = exercisesList;
    }
    public void addExercise(Exercise exercise) {
        this.exercisesList.add(exercise);
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Muscle{" +
                "name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
