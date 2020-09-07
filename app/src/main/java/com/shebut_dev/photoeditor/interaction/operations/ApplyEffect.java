package com.shebut_dev.photoeditor.interaction.operations;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.ImageProcessor;

public interface ApplyEffect {

    interface Callback{
        void onEffectApplied(Bitmap result);
        void onError(Exception e);
    }

    //void apply(ImageProcessor.EffectType effectType,
               //int intensity, Bitmap inputImage, ApplyEffect.Callback callback);
    void apply(ImageProcessor.EffectType effectType,
                float intensity, Bitmap inputImage, ApplyEffect.Callback callback);

}
