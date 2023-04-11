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
    private LiveData<List<Task>> tasksSortedAtoZ;
    @Nullable
    private LiveData<List<Task>>tasksSortedZtoA;
    @Nullable
    private LiveData<List<Task>> tasksSortedByTimeAsc;
    @Nullable
    private LiveData<List<Task>> tasksSortedByTimeDesc;
    //@Nullable
    //private LiveData<List<Project>> allProjects;
    @Nullable
    private MutableLiveData<Integer> sortType = new MutableLiveData<>();

    private MediatorLiveData<List<Task>> allTasksMediator = new MediatorLiveData<>();
    //private int isSorted = 0;
    public MainActivityViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        this.executor = executor;
    }

    public void init() {

        allTasks = mTaskRepository.getAllTasks();
        //allTasks = mTaskRepository.getAllSortedTasks(4);
        //allProjects = mProjectRepository.getAllProjects();
        sortType.setValue(0);

        tasksSortedAtoZ = mTaskRepository.getAllSortedTasks(1);
        tasksSortedZtoA = mTaskRepository.getAllSortedTasks(2);
        tasksSortedByTimeAsc = mTaskRepository.getAllSortedTasks(3);
        tasksSortedByTimeDesc = mTaskRepository.getAllSortedTasks(4);

        allTasksMediator.addSource(tasksSortedAtoZ, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    if(sortType.getValue() == 1)allTasksMediator.setValue(tasks);
                }
            }
        });

        allTasksMediator.addSource(allTasks, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (tasks != null) {
                    if (sortType.getValue() == 0) allTasksMediator.setValue(tasks);
                }
            }
        });
        allTasksMediator.addSource(tasksSortedZtoA, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    if(sortType.getValue() == 2)allTasksMediator.setValue(tasks);
                }
            }
        });
        allTasksMediator.addSource(tasksSortedByTimeAsc, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    if(sortType.getValue() == 3)allTasksMediator.setValue(tasks);
                }
            }
        });
        allTasksMediator.addSource(tasksSortedByTimeDesc, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    if(sortType.getValue() == 4)allTasksMediator.setValue(tasks);
                }
            }
        });
        allTasksMediator.addSource(sortType, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer != null && tasksSortedAtoZ.getValue()!= null && tasksSortedZtoA.getValue()!= null
                        && tasksSortedByTimeAsc.getValue()!= null && tasksSortedByTimeDesc.getValue()!= null){
                    if(integer == 1) allTasksMediator.setValue(tasksSortedAtoZ.getValue());
                    else if(integer == 2) allTasksMediator.setValue(tasksSortedZtoA.getValue());
                    else if(integer == 3) allTasksMediator.setValue(tasksSortedByTimeAsc.getValue());
                    else if(integer == 4) allTasksMediator.setValue(tasksSortedByTimeDesc.getValue());
                }
            }
        });
    }
    // -------------
    // FOR Projects
    // -------------
    public LiveData<List<Project>> getAllProjects() {
        return mProjectRepository.getAllProjects();
    }
    // -------------
    // FOR Tasks
    // -------------
    public LiveData<List<Task>> getAllTasks() {
        return this.allTasksMediator;
        //return mTaskRepository.getAllTasks();
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
    public void updateSorted(int sortType){
        //this.isSorted = isSorted;
        this.sortType.setValue(sortType);
    }
}
