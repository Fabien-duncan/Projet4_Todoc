package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * repository for the projects. Used to access the Room database with the use of the
 * appropriate DAO. Sends or retrieves data from the ViewModel
 */
public class ProjectRepository {
    private ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    /**
     * Returns the List of Project
     * @return a LiveData conatining a list of Projects
     */
    public LiveData<List<Project>> getAllProjects() { return this.mProjectDao.getAllProjects(); }

    /**
     * Adds a Project to the Room Database by using the addProject() method ot the DAO
     * a feature not used yet, but is put in place in order to implement later
     * @param project is the new Project to add to the database
     */
    public void addProject(Project project){
        mProjectDao.addProject(project);
    }
}
