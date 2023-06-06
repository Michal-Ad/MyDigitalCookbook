package com.example.mydigitalcookbook.data.repository

import android.app.Application
import com.example.mydigitalcookbook.data.local.ItemDao
import com.example.mydigitalcookbook.data.local.ItemsDatabase
import com.example.mydigitalcookbook.data.model.Item

class ItemRepository(application: Application) {

    private var itemDao: ItemDao?

    init {
        val db  = ItemsDatabase.getDatabase(application)
        itemDao = db.itemsDao()
    }

    fun getItems() = itemDao?.getItems()

    fun addItem(item: Item) {
        itemDao?.addItem(item)
    }

    fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }

    fun deleteAll() {
        itemDao?.deleteAll()
    }
}