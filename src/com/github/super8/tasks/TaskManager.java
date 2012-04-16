package com.github.super8.tasks;

import java.util.HashMap;
import java.util.Map;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.github.ignition.core.tasks.IgnitedAsyncTask;
import com.github.ignition.core.tasks.IgnitedAsyncTaskHandler;

@ContextSingleton
public class TaskManager {

  private Map<Integer, IgnitedAsyncTask<Context, ?, ?, ?>> tasks = new HashMap<Integer, IgnitedAsyncTask<Context, ?, ?, ?>>();

  public void registerTask(int taskId, IgnitedAsyncTask<Context, ?, ?, ?> task) {
    tasks.put(taskId, task);
  }

  public Map<Integer, IgnitedAsyncTask<Context, ?, ?, ?>> getTasks() {
    return tasks;
  }

  public void reconnectTasks(Context context) {
    for (int taskId : tasks.keySet()) {
      tasks.get(taskId).connect(context);
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void reconnectTasks(IgnitedAsyncTaskHandler handler) {
    for (int taskId : tasks.keySet()) {
      tasks.get(taskId).connect(handler);
    }
  }

  public void disconnectTasks() {
    for (int taskId : tasks.keySet()) {
      tasks.get(taskId).disconnect();
    }
  }

  public void clearTasks() {
    tasks.clear();
  }
  
  public void cancelAllTasks() {
    for (int taskId : tasks.keySet()) {
      tasks.get(taskId).cancel(true);
    }

  }
}
