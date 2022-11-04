package com.example.district13.sticker_user;

public class StickerUser {
    private final String username;
    private boolean isSelected;

    public String getUsername() {
        return username;
    }

    public StickerUser(String username) {
        this.username = username;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
