package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivityViewModel extends ViewModel {
    // REPOSITORIES
    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor executor;
    private String name;

    // DATA
    @Nullable
    private LiveData<List<Task>> allTasks;
    @Nullable
    private LiveData<List<Project>> allProjects;

    private MutableLiveData<List<Task>> allTasksMutable;
    public MainActivityViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        this.executor = executor;
    }

    public void init() {
        /*if(this.allTasks != null && allProjects != null) {
            return;
        }
        if(this.allTasks == null) allTasks = mTaskRepository.getAllTasks();
        if(this.allProjects == null) allProjects = mProjectRepository.getAllProjects();*/
        allTasks = mTaskRepository.getAllTasks();
        allProjects = mProjectRepository.getAllProjects();
    }

    // -------------
    // FOR Projects
    // -------------
    public LiveData<List<Project>> getAllProjects() { return allProjects;  }


/*    public LiveData<Project> getProject(long projectId){ return mProjectRepository.getProject(projectId); }
    public String getProjectName(long projectId){return name;}*/

    // -------------
    // FOR Tasks
    // -------------
    public LiveData<List<Task>> getAllTasks() {
        return this.allTasks;
    }


    public void addTask(long projectId, @NonNull String name) {
        executor.execute(() -> {
            mTaskRepository.addTask(new Task(projectId, name, new Date().getTime()));
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> mTaskRepository.deleteTask(taskId));

    }

    public void updateTask(Task task) {
        executor.execute(() -> mTaskRepository.updateTask(task));
    }

    public void addProject(Project project){
        executor.execute(() -> mProjectRepository.addProject(project));
    }
    public void sortTasks(String type){

    }
}
