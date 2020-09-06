package com.shebut_dev.photoeditor.core.op_storage;

import android.util.Log;

import androidx.annotation.NonNull;

//Операция по изменению изображения
public class Operation {

    private int propertiesLength;
    private int[] properties;
    private OperationType type;

    Operation(OperationType type, int[] properties){
        this.type = type;
        this.properties = properties;
    }

    void execute(){
        Log.d("EXECUTING OPERATION...", type.toString());
        switch (type){
            case CHANGE_BRIGHTNESS:
                //ImageEditor.imagePropertiesManager.changeBrightness(properties[0]);
                break;
            case SET_FILTER_NEGATIVE:
            case SET_FILTER_BLACK_AND_WHITE:
                break;
        }

    }

    public enum OperationType{
        CHANGE_BRIGHTNESS,
        SET_FILTER_NEGATIVE, SET_FILTER_BLACK_AND_WHITE,
    }

    @NonNull
    @Override
    public String toString() {
        return type.toString();
    }
}
