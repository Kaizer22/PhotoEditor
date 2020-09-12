package com.shebut_dev.photoeditor.interaction.operations;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.ImageProcessor;

public interface ApplyMatrixFilter {
    interface Callback{
        void onMatrixFilterApplied(Bitmap result);
        void onError(Exception e);
    }

    void apply(ImageProcessor.EffectType effectType,
               float intensity, Bitmap inputImage, ApplyMatrixFilter.Callback callback);
}
