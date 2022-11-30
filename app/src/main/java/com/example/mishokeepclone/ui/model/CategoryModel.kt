package com.example.mishokeepclone.ui.model

val cats = mutableListOf<CategoryModel>()

data class CategoryModel(
    val cat: String,
)

fun addCat() {
    cats.add(CategoryModel(
        "All"
    ))
    cats.add(CategoryModel(
        "Personal"
    ))
    cats.add(CategoryModel(
        "Work"
    ))

}

