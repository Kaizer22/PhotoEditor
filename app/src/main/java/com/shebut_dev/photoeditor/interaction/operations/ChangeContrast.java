package com.shebut_dev.photoeditor.interaction.operations;

import android.graphics.Bitmap;

public interface ChangeContrast {

    interface Callback{
        void onContrastChanged(Bitmap result);
        void onError(Exception e);
    }

    void execute(float intensity, Bitmap inputImage, ChangeContrast.Callback callback);
}
