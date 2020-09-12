package com.shebut_dev.photoeditor.presentation.mvp.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.shebut_dev.photoeditor.core.ImageProcessor;
import com.shebut_dev.photoeditor.interaction.operations.ApplyEffect;
import com.shebut_dev.photoeditor.interaction.operations.ApplyMatrixFilter;
import com.shebut_dev.photoeditor.interaction.operations.ChangeBrightness;
import com.shebut_dev.photoeditor.interaction.operations.ChangeContrast;
import com.shebut_dev.photoeditor.interaction.operations.implementations.ApplyMatrixFilterImpl;
import com.shebut_dev.photoeditor.presentation.mvp.view.PhotoEditorView;

public class PhotoEditorPresenter {

    private Bitmap sourceImage;
    private PhotoEditorView view;

    private ChangeBrightness changeBrightnessOperation;
    private ChangeContrast changeContrastOperation;
    private ApplyEffect applyEffectOperation;
    private ApplyMatrixFilter applyMatrixFilterOperation;

    public PhotoEditorPresenter(Bitmap sourceImage, ChangeBrightness changeBrightness,
                                ChangeContrast changeContrast,
                                ApplyEffect applyEffect,
                                ApplyMatrixFilter applyMatrixFilter){
        changeBrightnessOperation = changeBrightness;
        changeContrastOperation = changeContrast;
        applyEffectOperation = applyEffect;
        applyMatrixFilterOperation = applyMatrixFilter;

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

    public void changeImageContrast(float intensity){
        changeContrastOperation.execute(intensity, sourceImage,
                new ChangeContrast.Callback() {
                    @Override
                    public void onContrastChanged(Bitmap result) {
                        view.updateImage(result);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void applyEffect(float intensity){
        applyEffectOperation.apply(ImageProcessor.EffectType.DISCRETE_COLORS, intensity,
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

    public void applyMatrixFilter(float intensity){
        applyMatrixFilterOperation.apply(ImageProcessor.EffectType.MATRIX_FILTER_TEST, intensity,
                sourceImage, new ApplyMatrixFilter.Callback() {
                    @Override
                    public void onMatrixFilterApplied(Bitmap result) {
                        view.updateImage(result);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }


}
