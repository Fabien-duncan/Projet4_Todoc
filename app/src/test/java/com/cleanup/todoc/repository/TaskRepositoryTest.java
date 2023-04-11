package com.cleanup.todoc.repository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepositoryTest {
    private TaskRepository mTaskRepository;
    //private ProjectDao mProjectDao;
    private TaskDao mTaskDao;

    private List<Task> allTasks = new ArrayList<>();
    @Before
    public void setup(){
        mTaskDao = Mockito.mock(TaskDao.class);
        mTaskRepository = new TaskRepository(mTaskDao);

        allTasks.add(new Task(1L,"testTask_3", new Date().getTime()));
        allTasks.add(new Task(2L,"testTask_1", new Date().getTime()));
        allTasks.add(new Task(3L,"testTask_2", new Date().getTime()));
        allTasks.get(0).setId(1L);
        allTasks.get(1).setId(2L);
        allTasks.get(2).setId(3L);

        setupDaoMethods();
    }

    @Test
    public void tesGetAllTasks() {
        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>(allTasks));
        Mockito.doReturn(taskLiveData).when(mTaskDao).getAlltask();

        LiveData<List<Task>> result = mTaskRepository.getAllTasks();

        assertEquals(taskLiveData, result);
        assertEquals(taskLiveData.getValue().size(), 3);
        assertEquals(result.getValue().size(), 3);
        assertEquals(taskLiveData.getValue().get(0).getName(), allTasks.get(0).getName());
        assertEquals(taskLiveData.getValue().get(0).getName(), result.getValue().get(0).getName());
        Mockito.verify(mTaskDao).getAlltask();
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void testGetAllSortedTasks() {
        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>(allTasks));
        Mockito.doReturn(taskLiveData).when(mTaskDao).getSortedList(anyInt());

        LiveData<List<Task>> result = mTaskRepository.getAllSortedTasks(1);

        assertEquals(taskLiveData, result);
        Mockito.verify(mTaskDao).getSortedList(anyInt());
        Mockito.verifyNoMoreInteractions(mTaskDao);
    }

    @Test
    public void testAddTask() {
        Task newTask = new Task(1L,"testTask_3", new Date().getTime());
        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>(allTasks));
        Mockito.doReturn(taskLiveData).when(mTaskDao).getAlltask();
        int size = taskLiveData.getValue().size();

        mTaskRepository.addTask(newTask);
        verify(mTaskDao).addTask(newTask);
        verifyNoMoreInteractions(mTaskDao);

        LiveData<List<Task>> result = mTaskRepository.getAllTasks();
        assertEquals(result.getValue().size(), size+1);

        assertEquals(taskLiveData.getValue().get(size).getName(), allTasks.get(size).getName());
    }

    @Test
    public void testDeleteTask() {
        long taskID = allTasks.get(0).getId();
        LiveData<List<Task>> taskLiveData = Mockito.spy(new MutableLiveData<>(allTasks));
        Mockito.doReturn(taskLiveData).when(mTaskDao).getAlltask();
        int size = taskLiveData.getValue().size();

        mTaskRepository.deleteTask(taskID);
        verify(mTaskDao).deleteTask(taskID);
        verifyNoMoreInteractions(mTaskDao);

        LiveData<List<Task>> result = mTaskRepository.getAllTasks();
        assertEquals(result.getValue().size(), size-1);

    }

    @Test
    public void testUpdateTask() {
        Task updatedTask = new Task(1L,"testTask_3", new Date().getTime());

        mTaskRepository.updateTask(updatedTask);

        verify(mTaskDao).updateTask(updatedTask);
        verifyNoMoreInteractions(mTaskDao);
    }

    private void setupDaoMethods(){
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                for (Object rawTask : invocation.getArguments()) {
                    Task newTask=(Task)rawTask;

                    allTasks.add(newTask);
                }
                return(null);
            }
        }).when(mTaskDao).addTask(any());
        doAnswer(new Answer() {
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
        }).when(mTaskDao).deleteTask(any(Long.class));
    }
}