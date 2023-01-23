package com.example.mishokeepclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.databinding.SingletasklayoutBinding
import com.example.mishokeepclone.ui.screens.dashboard.DashboardFragmentDirections
import java.util.*
import kotlin.collections.ArrayList

class TasksAdapter  :
    ListAdapter<TaskEntity, TasksAdapter.TasksViewHolder>(NewsDiffCallBack()),Filterable {

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

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<TaskEntity>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(currentList)
            } else {
                for (item in currentList) {
                    if (item.priority.startsWith(constraint.toString().toLowerCase())) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<TaskEntity>)
        }

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