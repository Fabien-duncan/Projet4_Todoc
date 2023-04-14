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

/**
 * ViewModel class. This class is used to manage the iteraction between the View and the data.
 */
public class MainActivityViewModel extends ViewModel {
    // REPOSITORIES
    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor executor;

    // -------------
    // Live data for each type of Liter
    // -------------
    @Nullable
    private LiveData<List<Task>> tasksSortedAtoZ;
    @Nullable
    private LiveData<List<Task>>tasksSortedZtoA;
    @Nullable
    private LiveData<List<Task>> tasksSortedByTimeAsc;
    @Nullable
    private LiveData<List<Task>> tasksSortedByTimeDesc;
    @Nullable
    private MutableLiveData<Integer> sortType = new MutableLiveData<>();//used to decide which LiveData is placed inside the mediator

    private MediatorLiveData<List<Task>> allTasksMediator = new MediatorLiveData<>();//The live data that will be observed by the view
    public MainActivityViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        this.executor = executor;
        init();
    }

    /**
     * initialises all the different LiveData and sets up the observers for the MediatorLiveData.
     * This will make sure the Mediator contains the correct List to display in the View
     */
    public void init() {
        sortType.setValue(3);
        tasksSortedAtoZ = mTaskRepository.getAllSortedTasks(1);
        tasksSortedZtoA = mTaskRepository.getAllSortedTasks(2);
        tasksSortedByTimeAsc = mTaskRepository.getAllSortedTasks(3);
        tasksSortedByTimeDesc = mTaskRepository.getAllSortedTasks(4);

        // -------------
        // All the different observes for the different livedata and the sortType
        // -------------
        allTasksMediator.addSource(tasksSortedAtoZ, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    if(sortType.getValue() == 1)allTasksMediator.setValue(tasks);
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

    /**
     * returns the List of Projects
     * @return LiveData containing the List Of Projects
     */
    public LiveData<List<Project>> getAllProjects() {
        return mProjectRepository.getAllProjects();
    }
    // -------------
    // FOR Tasks
    // -------------
    /**
     * returns the List of Tasks
     * @return LiveData containing the List Of Tasks
     */
    public LiveData<List<Task>> getAllTasks() {
        return this.allTasksMediator;
    }

    /**
     * Adds a task to the Database by calling the addTask() method from the TaskRepository
     * @param projectId is the id of the project linked to the task
     * @param name is the name of the new project
     */
    public void addTask(long projectId, @NonNull String name) {
        executor.execute(() -> {
            mTaskRepository.addTask(new Task(projectId, name, new Date().getTime()));
        });
    }

    /**
     * removes a Task from the Room Database by using the deleteTask() method ot the repository
     * @param taskId is the identification number of the Task to delete from the database
     */
    public void deleteTask(long taskId) {
        executor.execute(() -> mTaskRepository.deleteTask(taskId));

    }

    /**
     * method used to update the sort type in order to sned the correct List of tasks to the View
     * @param sortType
     */
    public void updateSorted(int sortType){
        this.sortType.setValue(sortType);
    }

    // -------------
    // Features not yet implemented
    // -------------
    public void updateTask(Task task) {
        executor.execute(() -> mTaskRepository.updateTask(task));
    }
    public void addProject(Project project){
        executor.execute(() -> mProjectRepository.addProject(project));
    }
}
