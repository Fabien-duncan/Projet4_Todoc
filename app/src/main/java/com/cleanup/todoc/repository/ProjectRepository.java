package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {
    private ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) { mProjectDao = projectDao;
        //mProjectDao.addProject(new Project(5L, "Fabien", 0xFFA3CED2));
    //System.out.println("in repo name of project:" + mProjectDao.getAllProjects().getValue().get(0).getName());
      //  System.out.println("in repo name of project:");
    }

    // --- GET projects ---
    public LiveData<List<Project>> getAllProjects() { return this.mProjectDao.getAllProjects(); }

    public void addProject(Project project){
        mProjectDao.addProject(project);
    }

   /* public String getProjectName(long projectId){return mProjectDao.getProjectName(projectId);}

    public LiveData<Project> getProject(long projectId){ return this.mProjectDao.getProject(projectId); }*/
}
