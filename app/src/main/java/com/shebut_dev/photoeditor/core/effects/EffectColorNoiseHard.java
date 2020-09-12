package com.shebut_dev.photoeditor.core.effects;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.BaseEffect;

import java.util.Random;

public class EffectColorNoiseHard implements BaseEffect {
    @Override
    public Bitmap apply(Bitmap inputImage, float intensity) {
        Random r = new Random();
        int randomSeed = (int) (255 * intensity);

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int allPixels = width * height;
        int[] pixels = new int[allPixels];
        inputImage.getPixels(pixels, 0, width,
                0,0, width, height);

        //int alpha;
        byte red;
        byte green;
        byte blue;
        int pixel;

        for (int i = 0; i < allPixels; i++) {
            pixel = pixels[i];
            //alpha  = (pixel >> 24) & 0xff;
            red = (byte) ((pixel >> 16) & 0xff);
            green = (byte) ((pixel >> 8) & 0xff);
            blue = (byte) ((pixel) & 0xff);

            red += r.nextInt(randomSeed);
            green += r.nextInt(randomSeed);
            blue += r.nextInt(randomSeed);

            pixels[i] = pixels[i] & 0xFF000000 | ((int)red << 16) & 0x00FF0000 | ((int)green << 8) & 0x0000FF00 | (int)blue & 0x000000FF;
        }

        inputImage.setPixels(pixels, 0, width,
                0,0, width, height);
        return  inputImage;
    }
}
