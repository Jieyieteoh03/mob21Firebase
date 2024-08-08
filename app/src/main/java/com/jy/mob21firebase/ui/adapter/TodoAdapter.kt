package com.jy.mob21firebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jy.mob21firebase.data.model.Task
import com.jy.mob21firebase.databinding.ItemTodoBinding

class TodoAdapter (
    private var todos: List<Task>
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    var listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todos[position]
        holder.bind(item)
    }

    fun setTask(todos: List<Task>) {
        this.todos = todos
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding
    ):RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.desc
            binding.ivDelete.setOnClickListener{
                listener?.onDeleteItem(task)
            }
            binding.cvTodoItem.setOnClickListener {
                listener?.onClickItem(task)
            }
        }
    }

    interface Listener {
        fun onClickItem(task: Task)
        fun onDeleteItem(task: Task)
        fun onLongClick(task: Task)
    }

}