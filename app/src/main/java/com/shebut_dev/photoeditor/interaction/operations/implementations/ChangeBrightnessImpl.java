package com.shebut_dev.photoeditor.interaction.operations.implementations;

import android.graphics.Bitmap;

import com.shebut_dev.photoeditor.core.ImageProcessor;
import com.shebut_dev.photoeditor.interaction.executor.JobExecutor;
import com.shebut_dev.photoeditor.interaction.executor.PostExecutionThread;
import com.shebut_dev.photoeditor.interaction.operations.ChangeBrightness;
import com.shebut_dev.photoeditor.interaction.operations.ExecutableOperation;

public class ChangeBrightnessImpl extends ExecutableOperation
        implements ChangeBrightness  {

    private ChangeBrightness.Callback callback;

    private Bitmap inputImage;
    private int value;

    public ChangeBrightnessImpl(PostExecutionThread postExecutionThread,
                                JobExecutor jobExecutor) {
        super(postExecutionThread, jobExecutor);
    }

    @Override
    public void execute(int value, Bitmap inputImage, Callback callback) {
        this.inputImage = inputImage;
        this.value = value;
        super.execute();
        this.callback = callback;
    }

    @Override
    public void run() {
        try{
            ImageProcessor.changeBrightness(value, inputImage);
            this.postExecutionThread.post(() -> callback.onBrightnessChanged(inputImage));
        }catch (Exception e){
            this.postExecutionThread.post(() -> callback.onError(e));
        }
    }
}
