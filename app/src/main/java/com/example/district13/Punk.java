package com.example.district13;

import java.net.URL;

public class Punk {
    private final String name;
    private final String tagline;
    private final String first_brewed;
    private final String description;
    private final URL image_url;

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getFirst_brewed() {
        return first_brewed;
    }

    public String getDescription() {
        return description;
    }

    public URL getImage_url() {
        return image_url;
    }

    public Punk(String name, String tagline, String first_brewed, String description, URL image_url) {
        this.name = name;
        this.tagline = tagline;
        this.first_brewed = first_brewed;
        this.description = description;
        this.image_url = image_url;
    }
}
