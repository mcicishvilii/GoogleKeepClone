package com.example.mishokeepclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.databinding.SingletasklayoutBinding
import com.example.mishokeepclone.ui.screens.dashboard.DashboardFragmentDirections

class TasksAdapter  :
    ListAdapter<TaskEntity, TasksAdapter.TasksViewHolder>(
        NewsDiffCallBack()
    ) {

    private lateinit var itemClickListener: (TaskEntity, Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TasksViewHolder {
        val binding =
            SingletasklayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bindData()
    }

    inner class TasksViewHolder(private val binding: SingletasklayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var model: TaskEntity? = null

        fun bindData() {
            model = getItem(adapterPosition)
            binding.apply {
                tvTitle.text = model?.title
                tvDescription.text = model?.taskDescription
                binding.priority.text = model?.priority
            }
            binding.mainlayout.setOnLongClickListener {
                itemClickListener.invoke(model!!, adapterPosition)
                true
            }

            binding.mainlayout.setOnClickListener {
                val action = DashboardFragmentDirections.actionDashboardFragmentToUpdateFragment(model!!)
                binding.mainlayout.findNavController().navigate(action)
            }
        }
    }

    fun setOnItemClickListener(clickListener: (TaskEntity, Int) -> Unit) {
        itemClickListener = clickListener
    }

}

class NewsDiffCallBack :
    DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(
        oldItem: TaskEntity,
        newItem: TaskEntity
    ): Boolean {
        return oldItem.taskid== newItem.taskid
    }

    override fun areContentsTheSame(
        oldItem: TaskEntity,
        newItem: TaskEntity
    ): Boolean {
        return oldItem == newItem
    }


}