package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectRepository {
    private final ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) { mProjectDao = projectDao; }

    // --- GET projects ---
    public LiveData<Project> getAllProjects() { return this.mProjectDao.getAllProjects(); }
}
