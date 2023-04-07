package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;

    private TodocDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        taskDao = db.mTaskDao();
        projectDao = db.mProjectDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void createTaskAndReadIt() throws Exception {
        List<Task> tasks = taskDao.getAlltask().getValue();
        assertEquals(tasks.size(), 5);
    }
}