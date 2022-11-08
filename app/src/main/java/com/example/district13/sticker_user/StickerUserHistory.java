package com.example.district13.sticker_user;

import java.net.URL;

public class StickerUserHistory {
    private final String userName;
    private final URL stickerURL;
//    private final URL stickerSent;
//    private final URL stickerReceived;

    public StickerUserHistory(String userName, URL stickerURL) {
        this.userName = userName;
        this.stickerURL = stickerURL;
    }

    public String getUserName() {
        return userName;
    }

    public URL getStickerURL() {
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
