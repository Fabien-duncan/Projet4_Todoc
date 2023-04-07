package com.cleanup.todoc.repository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepositoryTest {
    private TaskRepository mTaskRepository;
    //private ProjectDao mProjectDao;
    private TaskDao mTaskDao;

    @Before
    public void setup(){
        mTaskDao = Mockito.mock(TaskDao.class);
        mTaskRepository = new TaskRepository(mTaskDao);
    }

    @Test
    public void tesGetAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.add(new Task(1L,"testTask_1", new Date().getTime()));
        allTasks.add(new Task(2L,"testTask_2", new Date().getTime()));
        allTasks.add(new Task(3L,"testTask_3", new Date().getTime()));

        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>(allTasks));
        Mockito.doReturn(taskLiveData).when(mTaskDao).getAlltask();

        LiveData<List<Task>> result = mTaskRepository.getAllTasks();

        assertEquals(taskLiveData, result);
        assertEquals(taskLiveData.getValue().size(), 3);
        assertEquals(result.getValue().size(), 3);
        assertEquals(taskLiveData.getValue().get(0).getName(), "testTask_1");
        assertEquals(taskLiveData.getValue().get(0).getName(), result.getValue().get(0).getName());
        Mockito.verify(mTaskDao).getAlltask();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void testGetAllSortedTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.add(new Task(1L,"testTask_3", new Date().getTime()));
        allTasks.add(new Task(2L,"testTask_1", new Date().getTime()));
        allTasks.add(new Task(3L,"testTask_2", new Date().getTime()));

        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>(allTasks));
        Mockito.doReturn(taskLiveData).when(mTaskDao).getSortedList(anyInt());

        LiveData<List<Task>> result = mTaskRepository.getAllSortedTasks(1);

        assertEquals(taskLiveData, result);
        Mockito.verify(mTaskDao).getSortedList(anyInt());
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void testAddTask() {

    }

    @Test
    public void testDeleteTask() {
    }

    @Test
    public void testUpdateTask() {
    }

    @Test
    public void testUpdateTasks() {
    }

    @Test
    public void testSetMutableTasks() {
    }
}