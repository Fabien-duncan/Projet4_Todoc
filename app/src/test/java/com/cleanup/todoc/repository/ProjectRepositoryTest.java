package com.cleanup.todoc.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
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
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryTest {
    private ProjectRepository mProjectRepository;
    private ProjectDao mProjectDao;

    private List<Project> allProjects = new ArrayList<>();

    @Before
    public void setup(){
        mProjectDao = Mockito.mock(ProjectDao.class);
        mProjectRepository = new ProjectRepository(mProjectDao);

        allProjects.add(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        allProjects.add(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
        allProjects.add(new Project(3L, "Projet Circus", 0xFFA3CED2));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                for (Object rawProject : invocation.getArguments()) {
                    Project newProject=(Project)rawProject;

                    allProjects.add(newProject);
                }
                return(null);
            }
        }).when(mProjectDao).addProject(ArgumentMatchers.any());

    }

    @Test
    public void testGetAllProjects() {
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
        Project newProject = new Project(5L, "test Porject", 0xFFEADAD1);
        LiveData<List<Project>> projectLiveData = Mockito.spy(new MutableLiveData<>(allProjects));
        Mockito.doReturn(projectLiveData).when(mProjectDao).getAllProjects();
        int size = projectLiveData.getValue().size();

        mProjectRepository.addProject(newProject);
        verify(mProjectDao).addProject(newProject);
        verifyNoMoreInteractions(mProjectDao);

        LiveData<List<Project>> result = mProjectRepository.getAllProjects();
        assertEquals(result.getValue().size(), size+1);

        assertEquals(projectLiveData.getValue().get(size).getName(), "test Porject");
    }
}