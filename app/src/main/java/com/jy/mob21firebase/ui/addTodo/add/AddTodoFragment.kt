package com.jy.mob21firebase.ui.addTodo.add


import androidx.fragment.app.viewModels
import com.jy.mob21firebase.ui.addTodo.base.BaseManageTodoFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTodoFragment : BaseManageTodoFragment() {
    override val viewModel: AddTodoViewModel by viewModels()
}