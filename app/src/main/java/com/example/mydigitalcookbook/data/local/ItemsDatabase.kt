package com.example.mydigitalcookbook.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mydigitalcookbook.data.model.Item

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase(){

    abstract fun itemsDao() : ItemDao

    companion object {

        @Volatile
        private var instance: ItemsDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(ItemsDatabase::class.java) {
            Room.databaseBuilder(context.applicationContext,
                ItemsDatabase::class.java,"items_database")
                .allowMainThreadQueries().build().also { instance = it }
        }
    }
}