package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {
    private ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    // --- GET projects ---
    public LiveData<List<Project>> getAllProjects() { return this.mProjectDao.getAllProjects(); }

    public void addProject(Project project){
        mProjectDao.addProject(project);
    }
}
