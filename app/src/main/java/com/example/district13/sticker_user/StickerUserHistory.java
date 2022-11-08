package com.example.district13.sticker_user;


public class StickerUserHistory {
    private final String userName;
    private final String stickerURL;
//    private final URL stickerSent;
//    private final URL stickerReceived;

    public StickerUserHistory(String userName, String stickerURL) {
        this.userName = userName;
        this.stickerURL = stickerURL;
    }

    public String getUserName() {
        return userName;
    }

    public String getStickerURL() {
        return stickerURL;
    }

//    public URL getStickerSent() {
//        return stickerSent;
//    }
//
//    public URL getStickerReceived() {
//        return stickerReceived;
//    }
}
