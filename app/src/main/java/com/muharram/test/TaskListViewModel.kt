package com.muharram.test

import androidx.lifecycle.ViewModel

class TaskListViewModel:ViewModel() {

    private val taskRepository = TaskRepository.get()
    val taskListLiveData = taskRepository.getTasks()

}