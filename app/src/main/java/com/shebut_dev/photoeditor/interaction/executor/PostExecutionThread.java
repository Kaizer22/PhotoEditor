package com.shebut_dev.photoeditor.interaction.executor;

public interface PostExecutionThread {
    void post(Runnable runnable);
}
