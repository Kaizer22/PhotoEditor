package com.shebut_dev.photoeditor.presentation.mvp.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.shebut_dev.photoeditor.core.ImageProcessor;
import com.shebut_dev.photoeditor.interaction.operations.ApplyEffect;
import com.shebut_dev.photoeditor.interaction.operations.ChangeBrightness;
import com.shebut_dev.photoeditor.presentation.mvp.view.PhotoEditorView;

public class PhotoEditorPresenter {

    private Bitmap sourceImage;
    private PhotoEditorView view;

    private ChangeBrightness changeBrightnessOperation;
    private ApplyEffect applyEffectOperation;

    public PhotoEditorPresenter(Bitmap sourceImage, ChangeBrightness changeBrightness,
                                ApplyEffect applyEffect){
        changeBrightnessOperation = changeBrightness;
        applyEffectOperation = applyEffect;
        this.sourceImage = sourceImage;
    }

    public void setView(PhotoEditorView view){
        this.view = view;
    }

    public void setSourceImage(Bitmap sourceImage) {
        this.sourceImage = sourceImage;
        //view.updateImage(sourceImage);
    }

    public Bitmap getSourceImage() {
        return sourceImage;
    }

    public void changeImageBrightness(int value){
        changeBrightnessOperation.execute(value, sourceImage, new ChangeBrightness.Callback() {
            @Override
            public void onBrightnessChanged(Bitmap result) {
                view.updateImage(result);
                Log.d("PRESENTER", "BR_CHANGED");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void applyEffect(float intensity){
        applyEffectOperation.apply(ImageProcessor.EffectType.COLOR_NOISE_SOFT, intensity,
                sourceImage, new ApplyEffect.Callback() {
                    @Override
                    public void onEffectApplied(Bitmap result) {
                        view.updateImage(result);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }


}
