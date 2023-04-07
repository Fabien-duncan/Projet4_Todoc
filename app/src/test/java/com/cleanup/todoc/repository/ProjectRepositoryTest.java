package com.cleanup.todoc.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryTest {
    private ProjectRepository mProjectRepository;
    private ProjectDao mProjectDao;

    @Before
    public void setup(){
        mProjectDao = Mockito.mock(ProjectDao.class);
        mProjectRepository = new ProjectRepository(mProjectDao);
    }

    @Test
    public void testGetAllProjects() {
        List<Project> allProjects = new ArrayList<>();
        allProjects.add(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        allProjects.add(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
        allProjects.add(new Project(3L, "Projet Circus", 0xFFA3CED2));

        LiveData<List<Project>> projectLiveData = Mockito.spy(new MutableLiveData<>(allProjects));
        Mockito.doReturn(projectLiveData).when(mProjectDao).getAllProjects();

        LiveData<List<Project>> result = mProjectRepository.getAllProjects();

        assertEquals(projectLiveData, result);
        assertEquals(projectLiveData.getValue().size(), 3);
        assertEquals(result.getValue().size(), 3);
        assertEquals(projectLiveData.getValue().get(0).getName(), "Projet Tartampion");
        assertEquals(projectLiveData.getValue().get(0).getName(), result.getValue().get(0).getName());
        Mockito.verify(mProjectDao).getAllProjects();
        Mockito.verifyNoMoreInteractions(mProjectDao);
    }
    @Test
    public void tesAddProject(){
        Project newProject = new Project(1L, "Projet Tartampion", 0xFFEADAD1);

        mProjectRepository.addProject(newProject);

        verify(mProjectDao).addProject(newProject);
        verifyNoMoreInteractions(mProjectDao);
    }



}