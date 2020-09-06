package com.shebut_dev.photoeditor.interaction.operations;

import android.graphics.Bitmap;

public interface ChangeBrightness {

    interface Callback{
        void onBrightnessChanged(Bitmap result);
        void onError(Exception e);
    }

    void execute(int value, Bitmap inputImage, Callback callback);
}
