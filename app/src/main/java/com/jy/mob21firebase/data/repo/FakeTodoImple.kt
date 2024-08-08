package com.jy.mob21firebase.data.repo

import com.jy.mob21firebase.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTodoImple: TodoRepo {
    private var tasks = mutableListOf<Task>()
    override fun getAllTasks(): Flow<List<Task>> = flow{emit(tasks)}

    override suspend fun addTask(task: Task): String? {
        val id = "${(1..10).random()}"
        val newTask = task.copy(id = id)
        tasks.add(newTask)
        return id
    }
    override suspend fun deleteTask(id: String) {
        tasks.removeIf { it.id == id }
    }

    override suspend fun updateTask(task: Task) {
        val findTask = tasks.find { it.id == task.id }
        val index = tasks.indexOf(findTask)
        tasks[index] = task
    }

    override suspend fun getTodoById(id: String): Task? {
        return tasks.find { it.id == id }
    }

}