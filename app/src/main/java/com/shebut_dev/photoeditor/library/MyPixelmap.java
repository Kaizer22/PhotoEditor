package com.shebut_dev.photoeditor.library;

import android.graphics.Bitmap;
import android.graphics.Color;

//Кастомная струтура данных для представления изображений
//Пока используется только RGB
public class MyPixelmap {

    private Pixel[][] pixelmap;
    private int height;
    private int width;

    int originalSize;
    int thisSize;


    public MyPixelmap(Bitmap source){
        height = source.getHeight();
        width = source.getWidth();

        pixelmap = new Pixel[height][width];
        originalSize = source.getByteCount();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j <width; j++) {
                pixelmap[i][j] =
                        new Pixel((byte)Color.red(source.getPixel(i,j)),
                                (byte)Color.green(source.getPixel(i,j)),
                                (byte)Color.blue(source.getPixel(i,j)));
            }
        }
    }


    public int getHeight() {
        return height;
    }

    public int getWidth(){
        return width;
    }
}
