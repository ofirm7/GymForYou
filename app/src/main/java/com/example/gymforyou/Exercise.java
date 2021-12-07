package com.example.gymforyou;

import android.widget.VideoView;

public class Exercise {
    private String nameOfExercise;
    private String creator;
    private String descriptionOfExercise, detailsOfExercise, url;
    private VideoView exampleVideo;

    public Exercise(String nameOfExercise, String creator, String descriptionOfExercise, String detailsOfExercise, String url, VideoView exampleVideo) {
        this.nameOfExercise = nameOfExercise;
        this.creator = creator;
        this.descriptionOfExercise = descriptionOfExercise;
        this.detailsOfExercise = detailsOfExercise;
        this.url = url;
        this.exampleVideo = exampleVideo;
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

    public String getDescriptionOfExercise() { return descriptionOfExercise; }

    public void setDescriptionOfExercise(String descriptionOfExercise) { this.descriptionOfExercise = descriptionOfExercise; }

    public VideoView getExampleVideo() { return exampleVideo; }

    public void setExampleVideo(VideoView exampleVideo) {
        this.exampleVideo = exampleVideo;
    }

    public String getDetailsOfExercise() { return detailsOfExercise; }

    public void setDetailsOfExercise(String detailsOfExercise) { this.detailsOfExercise = detailsOfExercise; }

    public void addDetailsOfExercise(String detailsOfExercise) { this.detailsOfExercise = detailsOfExercise; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
