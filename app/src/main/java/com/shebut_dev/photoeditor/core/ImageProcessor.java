package com.shebut_dev.photoeditor.core;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.effects.EffectBlackAndWhite;
import com.shebut_dev.photoeditor.core.effects.EffectColorNoiseHard;
import com.shebut_dev.photoeditor.core.effects.EffectColorNoiseSoft;
import com.shebut_dev.photoeditor.core.effects.EffectNegative;

public class ImageProcessor {

    public enum EffectType{
        BLACK_AND_WHITE,
        NEGATIVE,
        COLOR_NOISE_HARD,
        COLOR_NOISE_SOFT
    }

    public static Bitmap changeBrightness(int value, Bitmap inputImage){
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
        int pixel;

        for (int i = 0; i < allPixels; i++){
            pixel = pixels[i];
            //alpha  = (pixel >> 24) & 0xff;
            red = (pixel >> 16) & 0xff;
            green = (pixel >>  8) & 0xff;
            blue =  (pixel) & 0xff;

            red += value;
            green += value;
            blue += value;

            if (red > 255){
                red = 255;
            }else if (red < 0){
                red = 0;
            }
            if (green > 255){
                green = 255;
            }else if (green < 0){
                green = 0;
            }
            if (blue > 255){
                blue = 255;
            }else if (blue < 0){
                blue = 0;
            }
            pixels[i] = pixels[i] & 0xFF000000 | (red << 16) & 0x00FF0000 | (green << 8) & 0x0000FF00 | blue & 0x000000FF;
        }

        inputImage.setPixels(pixels, 0, width,
                0,0, width, height);

        return inputImage;
    }

    public static Bitmap applyEffect(float intensity, EffectType effect,
                                     Bitmap inputImage){
        switch (effect){
            case BLACK_AND_WHITE:
                inputImage = new EffectBlackAndWhite().apply(inputImage, intensity);
                break;
            case NEGATIVE:
                inputImage = new EffectNegative().apply(inputImage, intensity);
                break;
            case COLOR_NOISE_HARD:
                inputImage = new EffectColorNoiseHard().apply(inputImage, intensity);
                break;
            case COLOR_NOISE_SOFT:
                inputImage = new EffectColorNoiseSoft().apply(inputImage, intensity);
                break;

        }
        return inputImage;
    }
}
