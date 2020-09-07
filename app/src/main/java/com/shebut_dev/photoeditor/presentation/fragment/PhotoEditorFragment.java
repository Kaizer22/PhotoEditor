package com.shebut_dev.photoeditor.presentation.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PostProcessor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;

import com.denis_sh.photoeditor.R;
import com.shebut_dev.photoeditor.interaction.executor.JobExecutor;
import com.shebut_dev.photoeditor.interaction.executor.PostExecutionThread;
import com.shebut_dev.photoeditor.interaction.operations.ApplyEffect;
import com.shebut_dev.photoeditor.interaction.operations.ChangeBrightness;
import com.shebut_dev.photoeditor.interaction.operations.implementations.ApplyEffectImpl;
import com.shebut_dev.photoeditor.interaction.operations.implementations.ChangeBrightnessImpl;
import com.shebut_dev.photoeditor.presentation.executor.UIThread;
import com.shebut_dev.photoeditor.presentation.mvp.presenter.PhotoEditorPresenter;
import com.shebut_dev.photoeditor.presentation.mvp.view.PhotoEditorView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class PhotoEditorFragment extends Fragment implements PhotoEditorView {

    private ImageView editorField; //Впоследствии, кастомная View
    private PhotoEditorPresenter presenter;

    private void initializePresenter(Bitmap sourceImage){
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ChangeBrightness changeBrightness = new ChangeBrightnessImpl(postExecutionThread,
                jobExecutor);
        ApplyEffect applyEffect = new ApplyEffectImpl(postExecutionThread, jobExecutor);
        presenter = new PhotoEditorPresenter(sourceImage, changeBrightness, applyEffect);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo_editor, container, false);
        initInteractions(root);

        return root;
    }

    private void initInteractions(View root) {
        Button getImage = root.findViewById(R.id.get_photo_button);
        Button changeBrightness = root.findViewById(R.id.change_bright);
        Button applyBW = root.findViewById(R.id.button_apply_BW);

        applyBW.setOnClickListener(l ->
                presenter.applyEffect( 0.1f));
        changeBrightness.setOnClickListener(l ->
                presenter.changeImageBrightness(5));

        editorField = root.findViewById(R.id.temp_image_view);
        getImage.setOnClickListener(l -> getImageFromDevice());
    }

    private void getImageFromDevice() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(false)
                .setAllowFlipping(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setActivityTitle(getString(R.string.activity_title))
                .start(requireContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK){
                Uri uri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media
                            .getBitmap(getActivity().getContentResolver(), uri);
                    bitmap = bitmap.copy(bitmap.getConfig(), true);
                    initializePresenter(bitmap);
                    Log.d("PHOTO_EDITOR", uri.toString());
                    editorField.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                result.getError().printStackTrace();
            }
        }
    }

    @Override
    public void updateImage() {
        editorField.setImageBitmap(
                presenter.getSourceImage());
    }

    @Override
    public void updateImage(Bitmap bitmap) {
        editorField.setImageBitmap(bitmap);
    }
}
