package com.example.gymforyou;

public class Exercise {
    private String nameOfExercise;
    private String creator;
    private String description;

    public Exercise(String name, String creator, String description) {
        this.nameOfExercise = name;
        this.creator = creator;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + nameOfExercise + '\'' +
                ", creator='" + creator + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
