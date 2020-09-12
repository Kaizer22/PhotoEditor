package com.shebut_dev.photoeditor.interaction.operations.implementations;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.ImageProcessor;
import com.shebut_dev.photoeditor.interaction.executor.JobExecutor;
import com.shebut_dev.photoeditor.interaction.executor.PostExecutionThread;
import com.shebut_dev.photoeditor.interaction.operations.ApplyEffect;
import com.shebut_dev.photoeditor.interaction.operations.ApplyMatrixFilter;
import com.shebut_dev.photoeditor.interaction.operations.ExecutableOperation;

public class ApplyMatrixFilterImpl extends ExecutableOperation
        implements ApplyMatrixFilter{
    private ApplyMatrixFilter.Callback callback;

    private Bitmap inputImage;
    private float intensity;
    private ImageProcessor.EffectType effectType;

    public ApplyMatrixFilterImpl(PostExecutionThread postExecutionThread,
                           JobExecutor jobExecutor) {
        super(postExecutionThread, jobExecutor);
    }

    @Override
    public void run() {
        try{
            ImageProcessor.applyMatrixFilter(intensity, effectType, inputImage);
            callback.onMatrixFilterApplied(inputImage);
        }catch (Exception e){
            callback.onError(e);
        }
    }

    @Override
    public void apply(ImageProcessor.EffectType effectType, float intensity, Bitmap inputImage,
                      ApplyMatrixFilter.Callback callback) {
        this.effectType = effectType;
        this.intensity = intensity;
        this.inputImage = inputImage;
        super.execute();
        this.callback = callback;
    }
}
