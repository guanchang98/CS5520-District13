package com.example.district13.sticker_user_history;


public class StickerUserHistory {
    private final String userName;
    private final String stickerURL;
    private final String date;

    public StickerUserHistory(String userName, String stickerURL, String date) {
        this.userName = userName;
        this.stickerURL = stickerURL;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getStickerURL() {
        return stickerURL;
    }

    public String getDate() { return date; }

}
