package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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

    private final MediatorLiveData<List<Task>> allTasksMediator = new MediatorLiveData<>();
    private int isSorted = 0;
    public MainActivityViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        this.executor = executor;

       /* allTasks = this.mTaskRepository.getAllTasks();
        allProjects = this.mProjectRepository.getAllProjects();*/

        /*LiveData<List<Task>> tempLiveData = taskRepository.getAllTasks();
        allTasksMediator.addSource(tempLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                System.out.println("in on change");
                combine(tasks);
            }
        });*/

    }

    public void init() {
        /*if(this.allTasks != null && allProjects != null) {
            return;
        }
        if(this.allTasks == null) allTasks = mTaskRepository.getAllTasks();
        if(this.allProjects == null) allProjects = mProjectRepository.getAllProjects();*/

        allTasks = mTaskRepository.getAllTasks();
        //allTasks = mTaskRepository.getAllSortedTasks(1);
        allProjects = mProjectRepository.getAllProjects();

        /*allTasksMediator.addSource(allTasks, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                combine(tasks);
            }
        });*/
    }

    // -------------
    // FOR Projects
    // -------------
    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }


/*    public LiveData<Project> getProject(long projectId){ return mProjectRepository.getProject(projectId); }
    public String getProjectName(long projectId){return name;}*/

    // -------------
    // FOR Tasks
    // -------------
    public LiveData<List<Task>> getAllTasks() {
        if(isSorted > 0){
            System.out.println("getting sorted List");
            return mTaskRepository.getAllSortedTasks(isSorted);
        }
        else return this.allTasks;
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
    public void updateSorted(int isSorted){
        this.isSorted = isSorted;
    }
    /*private void combine(@Nullable List<Task> tasks){
        System.out.println("in combine");
        if(tasks == null) return;
        System.out.println("first task " + tasks.get(0).getName());
    }*/
}
