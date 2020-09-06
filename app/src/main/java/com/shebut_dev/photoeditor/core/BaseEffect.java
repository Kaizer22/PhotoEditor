package com.shebut_dev.photoeditor.core;

import android.graphics.Bitmap;

public interface BaseEffect {
    Bitmap apply(Bitmap sourceImage, int intensity);
}
