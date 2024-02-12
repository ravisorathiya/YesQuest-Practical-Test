package com.app.lock.code.testproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val userId: Int,
    val that: Int,
    val title: String,
    val body: String,
)