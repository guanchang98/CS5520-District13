package com.example.district13.teatalks_feed;

public class FeedItem {
    private final String posterID;
    private final String poster;
    private final String date;
    private final String imageURL;
    private final String avatarURL;
    private final String instruction;
    private boolean like;
    private String likeCount;
    private final String postTitle;
    private final String postTags;
    private boolean isFollowing;

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isLike() {
        return like;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostTags() {
        return postTags;
    }

    public FeedItem(String posterID, String poster, String date, String imageURL, String avatarURL,
                    String instruction, boolean like, String likeCount, String postTitle, String postTags, boolean isFollowing) {
        this.posterID = posterID;
        this.poster = poster;
        this.date = date;
        this.imageURL = imageURL;
        this.avatarURL = avatarURL;
        this.instruction = instruction;
        this.like = like;
        this.likeCount = likeCount;
        this.postTitle = postTitle;
        this.postTags = postTags;
        this.isFollowing = isFollowing;
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

    public String getId() {
        return posterID;
    }

    public String getLikeCount() {
        return likeCount;
    }
}
