package com.example.district13.sticker_user;

import java.net.URL;

public class StickerUserHistory {
    private final String userName;
    private final URL stickerSent;
    private final URL stickerReceived;

    public StickerUserHistory(String userName, URL stickerSent, URL stickerReceived) {
        this.userName = userName;
        this.stickerSent = stickerSent;
        this.stickerReceived = stickerReceived;
    }

    public String getUserName() {
        return userName;
    }

    public URL getStickerSent() {
        return stickerSent;
    }

    public URL getStickerReceived() {
        return stickerReceived;
    }
}
