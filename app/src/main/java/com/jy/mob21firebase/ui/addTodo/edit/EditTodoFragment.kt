package com.jy.mob21firebase.ui.addTodo.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jy.mob21firebase.R
import com.jy.mob21firebase.ui.addTodo.base.BaseManageTodoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTodoFragment: BaseManageTodoFragment() {
    override val viewModel: EditTodoViewModel by viewModels()

    override fun onBindData(view: View) {
        super.onBindData(view)

        binding?.btnSubmit?.text = getString(R.string.update)
    }

    override fun onBindView(view: View) {
        super.onBindView(view)

        lifecycleScope.launch {
            viewModel.task.collect{
                if (it != null) {
                    binding?.etTitle?.setText(it.title)
                    binding?.etDesc?.setText(it.desc)
                }
            }
        }
    }
}