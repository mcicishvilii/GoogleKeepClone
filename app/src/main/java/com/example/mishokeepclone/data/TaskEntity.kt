package com.example.mishokeepclone.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "Tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskid:Int,
    val title:String,
    val taskDescription:String
):Parcelable
