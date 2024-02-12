package com.app.lock.code.testproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.lock.code.testproject.data.model.Post


@Database(
    entities = [Post::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {


    companion object {
        const val DATABASE_NAME = "post_db"
    }

    abstract fun postDao(): PostDao
}