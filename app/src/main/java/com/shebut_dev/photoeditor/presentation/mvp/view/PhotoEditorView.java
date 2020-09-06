package com.shebut_dev.photoeditor.presentation.mvp.view;

import android.graphics.Bitmap;

public interface PhotoEditorView {

    void updateImage();
    void updateImage(Bitmap bitmap);
}
