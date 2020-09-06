package com.shebut_dev.photoeditor.library;

//Plan B
public class Pixel {
    private ColorModel model = ColorModel.RGB;
    byte[] color;

    public Pixel(byte red, byte green, byte blue){
        color = new byte[]{red, green, blue};
    }

    enum ColorModel{
        RGB, ARGB;
    }
}
