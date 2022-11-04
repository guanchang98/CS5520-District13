package com.example.district13.sticker_image;

public class StickerImage {
    private String imagePath;
    private boolean isSelected;

    public String getImagePath() {
        return imagePath;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public StickerImage(String imagePath) {
        this.imagePath = imagePath;
    }
}
