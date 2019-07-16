package com.sun_asterisk.myeditor03.custom.zoom;

public interface OnAnimationChangeListener {
    void onScale(float scaleFactor, float focalX, float focalY);

    float getScale();

    void onDrag(float dx, float dy);
}
