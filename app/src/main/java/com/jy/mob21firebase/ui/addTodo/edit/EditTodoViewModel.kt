package com.jy.mob21firebase.ui.addTodo.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jy.mob21firebase.data.model.Task
import com.jy.mob21firebase.data.repo.TodoRepo
import com.jy.mob21firebase.data.repo.TodoRepoReal
import com.jy.mob21firebase.ui.addTodo.base.BaseManageTodoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val repo: TodoRepo,
    state: SavedStateHandle
):BaseManageTodoViewModel(){
    val task: MutableStateFlow<Task?> = MutableStateFlow(null)
    private val taskId = state.get<String>("taskId")
    init {
       viewModelScope.launch {
           taskId?.let { id ->
               errorHandler { task.value = repo.getTodoById(id) }
           }
       }
    }
    override fun submitTask(title: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            task.value?.let {
                val newTask = it.copy(title = title, desc = desc,)
                    repo.updateTask(newTask)

            }?.let {
                _finish.emit("Update Successful")
            }
        }
    }
}