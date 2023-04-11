package com.cleanup.todoc.ui;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    private Executor mExecutor = Executors.newSingleThreadExecutor();
    @Mock
    private TaskRepository mTaskRepository = mock(TaskRepository.class);
    @Mock
    private ProjectRepository mProjectRepository = mock(ProjectRepository.class);
    private MainActivityViewModel mMainActivityViewModel;

    private  List<Project> dummyProject;
    private  List<Task> dummyTasks;

    @Before
    public void setUp(){
        dummyProject = getDummmyProject();
        dummyTasks = getDummyTasks(dummyProject);
        LiveData<List<Task>> allTasks = Mockito.spy(new MutableLiveData<>(dummyTasks));
        LiveData<List<Project>> allProjects = Mockito.spy(new MutableLiveData<>(dummyProject));

        doReturn(allProjects).when(mProjectRepository).getAllProjects();
        doReturn(allTasks).when(mTaskRepository).getAllSortedTasks(anyInt());

        setupRepositoryMethods();

        mMainActivityViewModel = new MainActivityViewModel(mTaskRepository,mProjectRepository,mExecutor);
    }

    @Test
    public void testGetAllProjects(){
        List<Project> projects = mMainActivityViewModel.getAllProjects().getValue();

        assertEquals(3, projects.size());

        verify(mProjectRepository).getAllProjects();
        verifyNoMoreInteractions(mProjectRepository);
    }

    @Test
    public void testGetAllTasks(){
        List<Task> tasks = mMainActivityViewModel.getAllTasks().getValue();

        assertEquals(4, tasks.size());

        verify(mTaskRepository).getAllSortedTasks(3);
        verifyNoMoreInteractions(mTaskRepository);
    }

    @Test
    public void addTask() {
        //Task newTask = new Task(1L,"testTask_5", new Date().getTime());
        int size = dummyTasks.size();

        mMainActivityViewModel.addTask(1L, "testTask_5");
        verify(mTaskRepository).addTask(any());
        verifyNoMoreInteractions(mTaskRepository);

        assertEquals(dummyTasks.size(), size+1);
    }

    @Test
    public void deleteTask() {
    }
    private List<Project> getDummmyProject() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        projects.add(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
        projects.add(new Project(3L, "Projet Circus", 0xFFA3CED2));
        return projects;
    }
    private List<Task> getDummyTasks(List<Project> projects) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(projects.get(0).getId(),"testTask_3", new Date().getTime()));
        tasks.add(new Task(projects.get(1).getId(),"testTask_1", new Date().getTime()));
        tasks.add(new Task(projects.get(2).getId(),"testTask_4", new Date().getTime()));
        tasks.add(new Task(projects.get(2).getId(),"testTask_2", new Date().getTime()));
        tasks.get(0).setId(1L);
        tasks.get(1).setId(2L);
        tasks.get(2).setId(3L);
        tasks.get(2).setId(4L);
        return tasks;
    }

    private void setupRepositoryMethods(){
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                for (Object rawTask : invocation.getArguments()) {
                    dummyTasks.add(any());
                }
                return(null);
            }
        }).when(mTaskRepository).addTask(any());
        /*doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                long id = (long)invocation.getArguments()[0];
                for(int i =0; i < allTasks.size(); i++){
                    if(allTasks.get(i).getId() == id){
                        System.out.println("dlt item: " + i);
                        allTasks.remove(i);
                    }
                }
                return(null);
            }
        }).when(mTaskDao).deleteTask(any(Long.class));*/
    }
}