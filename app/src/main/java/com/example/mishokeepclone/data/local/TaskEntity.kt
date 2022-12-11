package com.example.mishokeepclone.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "Tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskid:Int,
    val title:String,
    val taskDescription:String,
    val priority:String = "Personal",
    val time: String = ""
):Parcelable
