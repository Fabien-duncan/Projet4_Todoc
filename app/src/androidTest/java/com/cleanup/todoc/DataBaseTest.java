package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;

    private TodocDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        taskDao = db.mTaskDao();
        projectDao = db.mProjectDao();

        projectDao.addProject(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        projectDao.addProject(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
        projectDao.addProject(new Project(3L, "Projet Circus", 0xFFA3CED2));
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void createTaskAndReadIt() throws Exception {
        List<Task> tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);

        taskDao.addTask(new Task(1L,"testTask_3", new Date().getTime()));

        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 1);
    }
    @Test
    public void deleteTask(){
        List<Task> tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);
        taskDao.addTask(new Task(1L,"testTask_3", new Date().getTime()));
        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 1);

        taskDao.deleteTask(1L);

        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);

    }

    @Test
    public void Create3Tasks_getSortedTask_AtoZ(){
        List<Task> tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);
        createTasks();

        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(1));
        assertEquals(tasks.get(0).getName(), "aaa task");
        assertEquals(tasks.get(2).getName(), "zzz task");
    }

    @Test
    public void Create3Tasks_getSortedTask_ZtoA(){
        List<Task> tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);
        createTasks();

        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(2));
        assertEquals(tasks.get(2).getName(), "aaa task");
        assertEquals(tasks.get(0).getName(), "zzz task");
    }

    @Test
    public void Create3Tasks_getSortedTask_OldestFirst(){
        List<Task> tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);
        createTasks();

        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.get(0).getName(), "aaa task");
        assertEquals(tasks.get(1).getName(), "zzz task");
    }
    @Test
    public void Create3Tasks_getSortedTask_NewestFirst(){
        List<Task> tasks = TestUtils.getValueForTesting(taskDao.getSortedList(3));
        assertEquals(tasks.size(), 0);
        createTasks();

        tasks = TestUtils.getValueForTesting(taskDao.getSortedList(4));
        assertEquals(tasks.get(2).getName(), "aaa task");
        assertEquals(tasks.get(1).getName(), "zzz task");
    }

    private void createTasks(){
        taskDao.addTask(new Task(1L,"aaa task", new Date().getTime()));
        taskDao.addTask(new Task(2L,"zzz task", new Date().getTime()));
        taskDao.addTask(new Task(1L,"hhh task", new Date().getTime()));
    }
}