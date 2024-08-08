package com.jy.mob21firebase.ui.addTodo.add


import androidx.lifecycle.viewModelScope
import com.jy.mob21firebase.data.model.Extra
import com.jy.mob21firebase.data.model.Task
import com.jy.mob21firebase.data.repo.TodoRepo
import com.jy.mob21firebase.data.repo.TodoRepoReal
import com.jy.mob21firebase.ui.addTodo.base.BaseManageTodoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val repo: TodoRepo

): BaseManageTodoViewModel(){
    override fun submitTask(title: String, desc:String) {
        viewModelScope.launch (Dispatchers.IO){
            if (title.isNotEmpty() || desc.isNotEmpty()) {
                errorHandler {
                    val extra = Extra("Gelugor", "9AM")
                    val task = Task(title = title, desc = desc, extra = extra)
                    repo.addTask(task)
                }?.let{
                    _finish.emit("Add Successful")
                }
            } else {
                _error.emit("Please enter required field")
            }
        }
    }
}