package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) { this.mTaskDao = taskDao; }

    // --- GET ---
    public LiveData<List<Task>> getAllTasks(){ return this.mTaskDao.getAlltask(); }

    // --- CREATE ---
    public void addTask(Task task){ mTaskDao.addTask(task); }

    // --- DELETE ---
    public void deleteTask(long taskId){ mTaskDao.deleteTask(taskId); }

    // --- UPDATE ---
    public void updateTask(Task task){ mTaskDao.updateTask(task); }
}
