package com.cleanup.todoc.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao mTaskDao;
    private MutableLiveData<List<Task>> mutableTasks;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(TaskDao taskDao) {
        this.mTaskDao = taskDao;
        this.allTasks = mTaskDao.getAlltask();

        //mutableTasks.setValue(mTaskDao.getListTasks());
        //System.out.println("Name of first task: " +allTasks.getValue().get(0).getName());
        //mTaskDao.getAlltask().observe(this, this::updateTasks);
        //this.mutableTasks.setValue(mTaskDao.getListTasks());

    }

    // --- GET ---
    public LiveData<List<Task>> getAllTasks(){ return allTasks; }
    public LiveData<List<Task>> getAllSortedTasks(int isSorted){return mTaskDao.getSortedList(isSorted);}
    // --- CREATE ---
    public void addTask(Task task){ mTaskDao.addTask(task); }

    // --- DELETE ---
    public void deleteTask(long taskId){ mTaskDao.deleteTask(taskId); }

    // --- UPDATE ---
    public void updateTask(Task task){ mTaskDao.updateTask(task); }

    public void updateTasks(List<Task> tasks){
        this.mutableTasks.setValue(tasks);
        System.out.println("Name of first task: " +mutableTasks.getValue().get(0).getName());
    }

    public void setMutableTasks(List<Task> task) {
        this.mutableTasks.setValue(task);
    }

}
