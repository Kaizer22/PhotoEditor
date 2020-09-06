package com.shebut_dev.photoeditor.presentation.executor;

import android.os.Looper;
import android.os.Handler;

import com.shebut_dev.photoeditor.interaction.executor.PostExecutionThread;

public class UIThread implements PostExecutionThread {
    private static class LazyHolder {
        private static final UIThread INSTANCE = new UIThread();
    }

    public static UIThread getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final Handler handler;

    private UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
