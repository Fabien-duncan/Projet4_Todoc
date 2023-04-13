package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProject(Project project);

    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project ORDER BY id DESC")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT name FROM Project WHERE id = :projectId")
    String getProjectName(long projectId);

    @Query("DELETE FROM Task WHERE id = :projectid")
    int deleteProject(long projectid);
}
