package com.shebut_dev.photoeditor.core.effects;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.BaseEffect;

import java.util.Random;

public class EffectDiscreteColors implements BaseEffect {

    @Override
    public Bitmap apply(Bitmap inputImage, float intensity) {
        int factor = (int)(255 * intensity);

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int allPixels = width * height;
        int[] pixels = new int[allPixels];
        inputImage.getPixels(pixels, 0, width,
                0,0, width, height);

        //int alpha;
        int red;
        int green;
        int blue;
        int sum;
        int pixel;

        for (int i = 0; i < allPixels; i++) {
            pixel = pixels[i];
            //alpha  = (pixel >> 24) & 0xff;
            red = ((pixel >> 16) & 0xff);
            green =  ((pixel >> 8) & 0xff);
            blue =  ((pixel) & 0xff);
            sum = red + green + blue;

            if (sum > ((255 + factor) / 2) * 3){
                red = 255;
                green = 255;
                blue = 255;
            }else {
                red = 0;
                green = 0;
                blue = 0;
            }
            pixels[i] = pixels[i] & 0xFF000000 | (red << 16) & 0x00FF0000 | (green << 8) & 0x0000FF00 | blue & 0x000000FF;
        }

        inputImage.setPixels(pixels, 0, width,
                0,0, width, height);
        return  inputImage;
    }
}