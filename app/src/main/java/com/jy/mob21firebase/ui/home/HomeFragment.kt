package com.jy.mob21firebase.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jy.mob21firebase.R
import com.jy.mob21firebase.data.model.Task
import com.jy.mob21firebase.databinding.FragmentHomeBinding
import com.jy.mob21firebase.ui.adapter.TodoAdapter
import com.jy.mob21firebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_home

    private lateinit var adapter: TodoAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)

        setupAdapter()

        binding?.fabAdd?.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeToAddTodo()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.getAllTasks().collect{
                adapter.setTask(it)
                binding?.rvTodos?.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                binding?.llEmpty?.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setupAdapter() {
        adapter = TodoAdapter(emptyList())
        binding?.rvTodos?.adapter = adapter
        binding?.rvTodos?.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = object: TodoAdapter.Listener {
            override fun onClickItem(task: Task) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToEditTodo(task.id!!)
                )
            }

            override fun onDeleteItem(task: Task) {
                viewModel.deleteTask(task.id!!) // !! = If you are certain that the value can't be null, only then you use this operator
            }

            override fun onLongClick(task: Task) {
                TODO("Not yet implemented")
            }

        }
    }
}