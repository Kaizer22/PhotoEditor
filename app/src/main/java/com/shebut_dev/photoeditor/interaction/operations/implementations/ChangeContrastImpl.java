package com.shebut_dev.photoeditor.interaction.operations.implementations;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.ImageProcessor;
import com.shebut_dev.photoeditor.interaction.executor.JobExecutor;
import com.shebut_dev.photoeditor.interaction.executor.PostExecutionThread;
import com.shebut_dev.photoeditor.interaction.operations.ChangeBrightness;
import com.shebut_dev.photoeditor.interaction.operations.ChangeContrast;
import com.shebut_dev.photoeditor.interaction.operations.ExecutableOperation;

public class ChangeContrastImpl extends ExecutableOperation
        implements ChangeContrast {

    private ChangeContrast.Callback callback;

    private Bitmap inputImage;
    private float intensity;

    public ChangeContrastImpl(PostExecutionThread postExecutionThread,
                                JobExecutor jobExecutor) {
        super(postExecutionThread, jobExecutor);
    }

    @Override
    public void execute(float intensity,  Bitmap inputImage, Callback callback) {
        this.inputImage = inputImage;
        this.intensity = intensity;
        super.execute();
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            ImageProcessor.changeContrast(intensity, inputImage);
            this.postExecutionThread.post(() -> callback.onContrastChanged(inputImage));
        } catch (Exception e) {
            this.postExecutionThread.post(() -> callback.onError(e));
        }
    }
}