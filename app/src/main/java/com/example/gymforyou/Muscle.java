package com.example.gymforyou;

public class Muscle {
    private String name;
    private String creator;
    private String description;

    public Muscle() {
    }

    public Muscle(String name, String creator, String description) {
        this.name = name;
        this.creator = creator;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
