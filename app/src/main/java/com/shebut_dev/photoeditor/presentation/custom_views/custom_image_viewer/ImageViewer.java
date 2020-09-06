package com.shebut_dev.photoeditor.presentation.custom_views.custom_image_viewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

//Кастомная View для работы с изображением
public class ImageViewer extends View {
    Paint paint;
    int width;
    int height;
    int padding;
    Rect imageRectangle;
    public ImageViewer(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //if (!ImageEditor.isNullSource) {
            //canvas.drawBitmap(ImageEditor.sourceImage,0,0, paint);
            //canvas.drawBitmap(ImageEditor.sourceImage, null, imageRectangle, paint);
            //
        //}
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        padding = width/18;
        imageRectangle = new Rect(padding,padding,width - padding, height - padding);
    }

    public void onUpdate(){
        invalidate();
    }



}
