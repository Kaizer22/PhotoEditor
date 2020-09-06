package com.shebut_dev.photoeditor.interaction.operations;

import com.shebut_dev.photoeditor.interaction.Interactor;
import com.shebut_dev.photoeditor.interaction.executor.JobExecutor;
import com.shebut_dev.photoeditor.interaction.executor.PostExecutionThread;

public abstract class ExecutableOperation implements Interactor {

    protected final PostExecutionThread postExecutionThread;
    private final JobExecutor jobExecutor;

    public ExecutableOperation(PostExecutionThread postExecutionThread,
                               JobExecutor jobExecutor){
        this.jobExecutor = jobExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void execute(){
        this.jobExecutor.execute(this);
    }
}
