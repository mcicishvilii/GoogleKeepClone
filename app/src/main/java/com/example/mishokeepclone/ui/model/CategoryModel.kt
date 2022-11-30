package com.example.mishokeepclone.ui.model

val cats = mutableListOf(CategoryModel("All"),CategoryModel("Personal"),CategoryModel("Work"),)

data class CategoryModel(
    val cat: String,
)


