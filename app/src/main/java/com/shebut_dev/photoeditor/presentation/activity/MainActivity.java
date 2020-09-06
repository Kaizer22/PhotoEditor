package com.shebut_dev.photoeditor.presentation.activity;


import android.os.Bundle;
import com.denis_sh.photoeditor.R;

import com.shebut_dev.photoeditor.presentation.fragment.PhotoEditorFragment;

public class MainActivity extends BaseActivity {

    //TODO Сохранение состояний изображения
    //TODO Работа с изображением в отдельном потоке через очередь операций
    //TODO Меню - фрагмент
    //TODO Разобраться с созданием кастомной View для просмотра изображения

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhotoEditorFragment photoEditorFragment = new PhotoEditorFragment();
        replaceFragment(R.id.ft_container, photoEditorFragment);
    }

    //endregion special input handle
}