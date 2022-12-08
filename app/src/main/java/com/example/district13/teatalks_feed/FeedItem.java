package com.example.district13.teatalks_feed;

public class FeedItem {
    private final String poster;
    private final String date;
    private final String imageURL;
    private final String avatarURL;
    private final String instruction;
    private boolean like;

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isLike() {
        return like;
    }

    public FeedItem(String poster, String date, String imageURL, String avatarURL, String instruction) {
        this.poster = poster;
        this.date = date;
        this.imageURL = imageURL;
        this.avatarURL = avatarURL;
        this.instruction = instruction;
        this.like = false;
    }

    public String getPoster() {
        return poster;
    }

    public String getDate() {
        return date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getInstruction() {
        return instruction;
    }
}
