package com.shebut_dev.photoeditor.core.effects;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.BaseEffect;

//Реализаци эффекта "Негатив"
public class EffectNegative implements BaseEffect {
    @Override
    public Bitmap apply(Bitmap sourceImage, float intensity) {
        return sourceImage;
    }
}
