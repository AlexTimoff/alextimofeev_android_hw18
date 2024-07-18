package com.example.alextimofeev_android_hw18.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alextimofeev_android_hw18.entity.Photo

@Database(entities =[Photo::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}