package com.jy.mob21firebase.ui.addTodo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jy.mob21firebase.R
import com.jy.mob21firebase.databinding.FragmentBaseManageTodoBinding
import com.jy.mob21firebase.ui.base.BaseFragment
import kotlinx.coroutines.launch

abstract class BaseManageTodoFragment: BaseFragment<FragmentBaseManageTodoBinding>() {
    abstract override val viewModel: BaseManageTodoViewModel
    override fun getLayoutResource(): Int = R.layout.fragment_base_manage_todo

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.etTitle?.text.toString()
            val desc = binding?.etDesc?.text.toString()

            viewModel.submitTask(title,desc)
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.finish.collect{
                findNavController().popBackStack()
            }
        }
    }

}