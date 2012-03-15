package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.github.ignition.core.tasks.IgnitedAsyncTask;
import com.github.ignition.core.tasks.IgnitedAsyncTaskHandler;
import com.github.super8.tasks.TaskManager;
import com.google.inject.Inject;

public abstract class TaskManagingFragment<ReturnT> extends RoboFragment implements
    IgnitedAsyncTaskHandler<Context, Void, ReturnT> {

  @Inject
  private TaskManager taskManager;

  public TaskManagingFragment() {
    setRetainInstance(true);
  }

  @Override
  public Activity getContext() {
    return getActivity();
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    taskManager.reconnectTasks(this);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    taskManager.disconnectTasks();
  }

  @Override
  public void onTaskStarted(Context context) {
  }

  @Override
  public void onTaskProgress(Context context, Void... progress) {
  }

  @Override
  public void onTaskCompleted(Context context, ReturnT result) {
  }

  @Override
  public void onTaskFailed(Context context, Exception error) {
  }

  protected void addTask(int taskId, IgnitedAsyncTask<Context, ?, ?, ?> task) {
    taskManager.registerTask(taskId, task);
    task.connect(getActivity());
  }
}
