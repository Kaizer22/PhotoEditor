package com.shebut_dev.photoeditor.core;

import android.graphics.Bitmap;
import android.util.Log;

import com.shebut_dev.photoeditor.core.effects.EffectBlackAndWhite;
import com.shebut_dev.photoeditor.core.effects.EffectColorNoiseHard;
import com.shebut_dev.photoeditor.core.effects.EffectColorNoiseSoft;
import com.shebut_dev.photoeditor.core.effects.EffectDiscreteColors;
import com.shebut_dev.photoeditor.core.effects.EffectNegative;

public class ImageProcessor {

    public enum EffectType{
        BLACK_AND_WHITE,
        NEGATIVE,
        COLOR_NOISE_HARD,
        COLOR_NOISE_SOFT,
        DISCRETE_COLORS,

        MATRIX_FILTER_1,
        MATRIX_FILTER_2,
        MATRIX_FILTER_TEST
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

    public static Bitmap changeContrast(float intensity, Bitmap inputImage){
        int N = (int) (intensity * 100);
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

        Log.d("IMG_PROCESSOR", "changing contrast");
        for (int i = 0; i < allPixels; i++){
            pixel = pixels[i];
            //alpha  = (pixel >> 24) & 0xff;
            red = red(pixel);
            green = green(pixel);
            blue =  blue(pixel);

            if (intensity > 0){
                red = ((red * 100 - N * 128) / (100- N));
                green = ((green * 100 - N * 128) / (100- N));
                blue = ((blue * 100 - N * 128) / (100- N));
            }else {
                red = ((red * (100 - (-N)) + (-N) * 128) / 100);
                green = ((green * (100 - (-N)) + (-N) * 128) / 100);
                blue = ((blue * (100 - (-N)) + (-N) * 128) / 100);
            }

            red = constrain(red, 0, 255);
            green = constrain(green, 0, 255);
            blue = constrain(blue, 0,255);
            pixels[i] = color(red, green, blue);
        }

        inputImage.setPixels(pixels, 0, width,
                0,0, width, height);
        Log.d("IMG_PROCESSOR", "contrast changed");
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
            case DISCRETE_COLORS:
                inputImage = new EffectDiscreteColors().apply(inputImage, intensity);

        }
        return inputImage;
    }

    public static Bitmap applyMatrixFilter(float intensity, EffectType effectType,
                                           Bitmap inputImage){
        //Получение матрицы свертки
        float [][] filterMatrix;
        switch (effectType){
            case MATRIX_FILTER_1:
                filterMatrix = MatrixFilterProvider.MATRIX_FILTER_HIGHER_CLARITY;
                break;
            case MATRIX_FILTER_2:
                filterMatrix = MatrixFilterProvider.MATRIX_FILTER_HIGHER_BLIK;
                break;
            case MATRIX_FILTER_TEST:
                filterMatrix = MatrixFilterProvider.MATRIX_FILTER_TEST;
                break;
            default:
                filterMatrix = new float[3][3];
        }
        int matrixHeight = filterMatrix.length;
        int matrixWidth = filterMatrix[0].length;

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int allPixels = width * height;
        int[] pixels = new int[allPixels];
        inputImage.getPixels(pixels, 0, width,
                0,0, width, height);

        int bufHeight = height + 2 * (matrixHeight / 2);
        int bufWidth = width + 2 * (matrixWidth / 2);
        int[][] bufPixels = new int[bufHeight][bufWidth];
        int[][] operatingFrame = new int[matrixHeight][matrixWidth];

        //Получение промежуточного изображения большего размера
        // для решения проблемы граничных условий
        //for (int i = 0; i < bufHeight; i++){
            //for (int j = 0; j < bufWidth ; j++) {

               // if (i < matrixHeight / 2){
                   // if (j < matrixWidth / 2){
                       // bufPixels[i][j] = pixels[0];
                    //}else if(j > matrixWidth / 2 && j < (bufWidth - matrixWidth / 2)) {
                        //bufPixels[i][j] = pixels[ j - matrixWidth / 2];
                    //}else {
                       // bufPixels[i][j] = pixels[width - 1];
                    //}
                //}else if (i > (bufHeight - matrixHeight / 2)){
                        //if(j < matrixWidth / 2){
                            //bufPixels[i][j] = pixels[allPixels - width];
                        //}else if(j > matrixWidth / 2 && j < (bufWidth - matrixWidth / 2) ){
                            //bufPixels[i][j] = pixels[
                                    //allPixels - (width - (j - matrixWidth / 2))];
                        //}else{
                           // bufPixels[i][j] = pixels[allPixels - 1];
                       // }
                //}else {
                   // if (j < matrixWidth / 2) {
                       // bufPixels[i][j] = pixels[(i - matrixHeight / 2) * width];
                    //} else if (j > matrixWidth && j < (bufWidth - matrixWidth / 2)) {
                       // bufPixels[i][j] = pixels[(i - matrixHeight / 2) * width
                                //+ (j - matrixWidth / 2)];
                    //} else {
                        //bufPixels[i][j] = pixels[(i - matrixHeight / 2 + 1) * width];
                    //}
               //}
            //}
       // }

        //int alpha;
        int red;
        int green;
        int blue;
        int pixel;

        Log.d("IMG_PROCESSOR", "applying matrix filter");
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width ; j++) {
                pixels[i * width + j] = convolution(j, i, filterMatrix,
                        width, filterMatrix.length, intensity, pixels) ;
            }
        }
        inputImage.setPixels(pixels, 0, width,
                0,0, width, height);
        return  inputImage;
    }

    private static int convolution(int x, int y, float[][] matrix, int width,
                                   int matrixsize, float normCoeff, int[] pixels)
    {
        int rtotal = 0;
        int gtotal = 0;
        int btotal = 0;
        int offset = matrixsize / 2;
        for (int i = 0; i < matrixsize; i++){
            for (int j= 0; j < matrixsize; j++){
                // Текущий пиксель
                int xLoc = x + j - offset;
                int yLoc = y + i - offset;
                int loc = xLoc + width * yLoc;
                // Проверка выхода за границы изображения
                loc = constrain(loc, 0, pixels.length-1);
                // Применяем метод свертки
                rtotal += (red(pixels[loc]) * matrix[i][j]);
                gtotal += (green(pixels[loc]) * matrix[i][j]);
                btotal += (blue(pixels[loc]) * matrix[i][j]);
            }
        }
        // Убедимся, что цветовые каналы не вышли из диапазонов
        rtotal = (int) (constrain(rtotal,0,255) * normCoeff);
        gtotal = (int) (constrain(gtotal,0,255) * normCoeff);
        btotal = (int) (constrain(btotal,0,255) * normCoeff);
        // Вернем пиксель
        return color(rtotal, gtotal, btotal);
    }


    private static int red(int color){
        return (color >> 16) & 0xff;
    }

    private static int green(int color){
        return (color >>  8) & 0xff;
    }

    private static int blue(int color){
        return  (color) & 0xff;
    }

    private static int constrain(int value, int bor1, int bor2){
        if (value < bor1){
            return bor1;
        }else if(value > bor2){
            return bor2;
        }else return value;
    }
    private static float constrain(float value, float bor1, float bor2){
        if (value < bor1){
            return bor1;
        }else if(value > bor2){
            return bor2;
        }else return value;
    }

    private static int color(int red, int green, int blue){
        return (red << 16) & 0x00FF0000 | (green << 8) & 0x0000FF00 | blue & 0x000000FF;
    }
}
