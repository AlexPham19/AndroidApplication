package com.example.androidapplication;

public class Music {
    private String name;
    private String artist;
    private String duration;
    private int image;

    public Music(String name, String artist, String duration, int image) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
