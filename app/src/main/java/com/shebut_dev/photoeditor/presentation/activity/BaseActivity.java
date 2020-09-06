package com.shebut_dev.photoeditor.presentation.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void initializeActivity(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(savedInstanceState);
    }

    public void addFragment(int containerId, Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        if (fragment != null){
            transaction.add(containerId, fragment)
                    .commit();
        }
    }

    public void replaceFragment(int containerId, Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        if (fragment != null){
            transaction.replace(containerId, fragment)
                    .commit();
        }
    }
}
