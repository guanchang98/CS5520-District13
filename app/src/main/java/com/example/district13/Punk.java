package com.example.district13;

public class Punk {
    private final String name;

    private final String first_brewed;

    public Punk(String name, String first_brewed) {
        this.name = name;
        this.first_brewed = first_brewed;
    }

    public String getName() {
        return name;
    }

    public String getFirstBrewed() {
        return first_brewed;
    }
}
