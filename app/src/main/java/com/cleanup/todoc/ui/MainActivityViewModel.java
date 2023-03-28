package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivityViewModel extends ViewModel {
    // REPOSITORIES
    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Task>> allTasks;
    @Nullable
    private LiveData<List<Project>> allProjects;

    public MainActivityViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        this.executor = executor;
    }

    public void init() {
        if(this.allTasks != null && allProjects != null) {
            return;
        }
        if(this.allTasks == null) allTasks = mTaskRepository.getAllTasks();
        if(this.allProjects == null) allProjects = mProjectRepository.getAllProjects();
    }

    // -------------
    // FOR Projects
    // -------------
    public LiveData<List<Project>> getAllProjects() { return this.allProjects;  }

    // -------------
    // FOR Tasks
    // -------------
    public LiveData<List<Task>> getAllTasks() {
        return this.allTasks;
    }

    public void addTask(long projectId, @NonNull String name, long creationTimestamp) {
        executor.execute(() -> {
            mTaskRepository.addTask(new Task(projectId, name, creationTimestamp));
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> mTaskRepository.deleteTask(taskId));

    }

    public void updateTask(Task task) {
        executor.execute(() -> mTaskRepository.updateTask(task));
    }
}
