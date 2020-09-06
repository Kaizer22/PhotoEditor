package com.shebut_dev.photoeditor.core.properties;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;


//Изменение параметров изображения
public class ImagePropertiesManager {

    //Изменение яркости изображения посредством добавления factor ко всем RGB составляющим
    public void changeBrightness(int factor){
        Log.d("DOING STUFF", "-");
        //Bitmap image = ImageEditor.sourceImage;
        int pixelBuf;
        int red, green, blue ;
        //for (int i = 0; i < ImageEditor.imageHeight; i++){
           // for (int j = 0; j < ImageEditor.imageWidth; j++){
               //pixelBuf = image.getPixel(j, i);
                //red = (Color.red(pixelBuf) + factor);
                //if (red > 255){
                  // red -= 255;
               // }else if (red < 0){
                    //red = 255 + red;
               // }
               // green = (Color.green(pixelBuf) + factor);
               // if (green > 255){
                  //  green -= 255;
               // }else if (green < 0){
                    //green = 255 + green;
                //}
                //blue = (Color.blue(pixelBuf) + factor);
                //if (blue > 255){
                    //blue -= 255;
                //}else if (blue < 0){
                    //blue = 255 + blue;
                //}
                //image.setPixel(j, i, Color.argb(Color.alpha(pixelBuf),red, green, blue));
           // }
       // }
       // Log.d("FINISHED STUFF", "-");
        //ImageEditor.setSourceImage(image);
    }
}
