package com.cleanup.todoc.repository;


import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
/**
 * repository for the Tasks. Used to access the Room database with the use of the
 * appropriate DAO. Sends or retrieves data from the ViewModel
 */
public class TaskRepository {
    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        this.mTaskDao = taskDao;
    }

    /**
     * Returns the List of Tasks retrieve from the database
     * @param sortType is the type of sorting required on the List of Tasks
     * @return LiveData containing the List of Tasks
     */
    public LiveData<List<Task>> getAllSortedTasks(int sortType){return mTaskDao.getSortedList(sortType);}

    /**
     * Adds a Task to the Room Database by using the addTask() method ot the DAO
     * @param task is the new Task to add to the database
     */
    public void addTask(Task task){ mTaskDao.addTask(task); }

    /**
     * removes a Task from the Room Database by using the deleteTask() method ot the DAO
     * @param taskId is the identification number of the Task to delete from the database
     */
    public void deleteTask(long taskId){ mTaskDao.deleteTask(taskId); }

    /**
     * Updates a Task from the Room Database by using the updateTask() method ot the DAO
     * a feature not used yet, but is put in place in order to implement later
     * @param task is the new Task to add to the database
     */
    public void updateTask(Task task){ mTaskDao.updateTask(task); }

}
