package com.shebut_dev.photoeditor.core.effects;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.BaseEffect;

//Реализация эффекта "ЧБ"
public class EffectBlackAndWhite implements BaseEffect {
    @Override
    public Bitmap apply(Bitmap sourceImage, int intensity) {
        return  sourceImage;
    }
}
