package com.jy.mob21firebase.ui.home

import androidx.lifecycle.viewModelScope
import com.jy.mob21firebase.data.repo.TodoRepo
import com.jy.mob21firebase.data.repo.TodoRepoReal
import com.jy.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: TodoRepo
):BaseViewModel() {

//    init {
//        viewModelScope.launch {
//            errorHandler {
//                repo.getTaskByTitle("h")
//            }?.let {
//                Log.d("debugging_title", it.toString())
//            }
//        }
//    }
    fun getAllTasks() = repo.getAllTasks()

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            errorHandler{
                repo.deleteTask(taskId)
            } ?.let {
                _finish.emit("Delete Successful")
            }
            /*
                Another way to write the code: errorHandler { func = {repo.deleteTask(taskId)} }
            */

        }
    }
}