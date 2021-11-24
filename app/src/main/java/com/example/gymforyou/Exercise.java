package com.example.gymforyou;

public class Exercise {
    private String nameOfExercise;
    private String creator;
    private String descriptionOfExercise;

    public Exercise(String name, String creator, String descriptionOfExercise) {
        this.nameOfExercise = name;
        this.creator = creator;
        this.descriptionOfExercise = descriptionOfExercise;
    }

    public Exercise() {
    }

    public String getName() {
        return nameOfExercise;
    }

    public void setName(String name) {
        this.nameOfExercise = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescriptionOfExercise() {
        return descriptionOfExercise;
    }

    public void setDescriptionOfExercise(String descriptionOfExercise) {
        this.descriptionOfExercise = descriptionOfExercise;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + nameOfExercise + '\'' +
                ", creator='" + creator + '\'' +
                ", description='" + descriptionOfExercise + '\'' +
                '}';
    }
}
