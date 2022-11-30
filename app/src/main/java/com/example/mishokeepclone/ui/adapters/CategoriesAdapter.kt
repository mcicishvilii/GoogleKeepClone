package com.example.mishokeepclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.databinding.SingleCatLayoutBinding
import com.example.mishokeepclone.databinding.SingletasklayoutBinding
import com.example.mishokeepclone.ui.model.CategoryModel
import com.example.mishokeepclone.ui.screens.dashboard.DashboardFragmentDirections

class CategoriesAdapter  :
    ListAdapter<CategoryModel, CategoriesAdapter.CategoriesViewHolder>(
        CategoriesDiffCallback()
    ) {

    private lateinit var itemClickListener: (CategoryModel, Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CategoriesViewHolder {
        val binding =
            SingleCatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bindData()
    }

    inner class CategoriesViewHolder(private val binding: SingleCatLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var model: CategoryModel? = null

        fun bindData() {
            model = getItem(adapterPosition)
            binding.apply {
                tvCategory.text = model?.cat

            }
            binding.mainlayout.setOnClickListener() {
                itemClickListener.invoke(model!!, adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(clickListener: (CategoryModel, Int) -> Unit) {
        itemClickListener = clickListener
    }

}

class CategoriesDiffCallback :
    DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(
        oldItem: CategoryModel,
        newItem: CategoryModel
    ): Boolean {
        return oldItem.cat== newItem.cat
    }

    override fun areContentsTheSame(
        oldItem: CategoryModel,
        newItem: CategoryModel
    ): Boolean {
        return oldItem == newItem
    }


}