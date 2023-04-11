package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;


@Dao
public interface TaskDao {
    /*@Query("SELECT * FROM Task")
    LiveData<List<Task>> getAlltask();*/
    @Query("SELECT * FROM Task")
    List<Task> getListTasks();
    @Query("Select * FROM Task ORDER BY " +
            "CASE WHEN :sortType = 1 THEN name END ASC, " +
            "CASE WHEN :sortType = 2 THEN name END DESC," +
            "CASE WHEN :sortType = 3 THEN creationTimestamp END ASC," +
            "CASE WHEN :sortType = 4 THEN creationTimestamp END DESC")
    LiveData<List<Task>> getSortedList(int sortType);
    @Insert
    long addTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);
}
