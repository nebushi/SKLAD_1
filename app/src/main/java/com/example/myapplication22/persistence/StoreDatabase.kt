package com.example.myapplication22.persistence

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication22.domain.Item
import com.example.myapplication22.domain.Store
import com.example.myapplication22.domain.StoredItem
import kotlin.collections.ArrayList

class StoreDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "store.db"
        private const val DATABASE_VERSION = 1
        const val STORE_TABLE_NAME = "store"
        const val STORE_ID = "id"
        const val STORE_NAME = "name"
        const val STORE_ADDRESS = "address"

        const val ITEM_TABLE_NAME= "item"
        const val ITEM_ID = "id"
        const val ITEM_NAME = "name"
        const val ITEM_DESC = "description"

        const val STOREDITEM_TABLE_NAME = "stored_item"
        const val STOREDITEM_ID = "id"
        const val STOREDITEM_STORE_ID ="store_id"
        const val STOREDITEM_ITEM_ID = "item_id"
        const val STOREDITEM_PLACE = "place"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val storeTable ="""
            CREATE TABLE $STORE_TABLE_NAME(
            $STORE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $STORE_NAME TEXT,
            $STORE_ADDRESS TEXT)                        
        """.trimIndent()
        db?.execSQL(storeTable)

        val itemTable ="""
            CREATE TABLE $ITEM_TABLE_NAME(
            $ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $ITEM_NAME TEXT,
            $ITEM_DESC TEXT)                        
        """.trimIndent()
        db?.execSQL(itemTable)


        val storedItemsTable ="""
        CREATE TABLE $STOREDITEM_TABLE_NAME(
            $STOREDITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $STOREDITEM_STORE_ID INTEGER,
            $STOREDITEM_ITEM_ID INTEGER, 
            $STOREDITEM_PLACE TEXT,
            FOREIGN KEY( $STOREDITEM_STORE_ID) REFERENCES $STORE_TABLE_NAME($STORE_ID),
            FOREIGN KEY( $STOREDITEM_ITEM_ID) REFERENCES $ITEM_TABLE_NAME($ITEM_ID)        
        )
        """.trimIndent()
        db?.execSQL(storedItemsTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $STOREDITEM_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $STORE_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $ITEM_TABLE_NAME")
        onCreate(db)
    }

    fun reset() {
        val db = writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS $STOREDITEM_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $STORE_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $ITEM_TABLE_NAME")
        onCreate(db)
        db.close()
    }

    fun addStore(store: Store) {
        val values = ContentValues().apply {
            put(STORE_NAME, store.name)
            put(STORE_ADDRESS, store.address)
        }
        val db = writableDatabase
        db.insert(STORE_TABLE_NAME, null, values)
        db.close()
    }
    fun addItem(item: Item) {
        val values = ContentValues().apply {
            put(ITEM_NAME, item.name)
            put(ITEM_DESC, item.description)
        }
        val db = writableDatabase
        db.insert(ITEM_TABLE_NAME, null, values)
        db.close()
    }

    fun getStores(): List<Store> {
        val stores = ArrayList<Store>()
        val db = readableDatabase
        val cursor = db.query(STORE_TABLE_NAME, arrayOf(STORE_ID, STORE_NAME, STORE_ADDRESS), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val store =  Store(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(STORE_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(STORE_NAME)),
                address = cursor.getString(cursor.getColumnIndexOrThrow(STORE_ADDRESS))
            )
            stores.add(store)
        }
        cursor.close()
        db.close()
        return stores
    }
    fun getItems(): List<Item> {
        val items = ArrayList<Item>()
        val db = readableDatabase
        val cursor = db.query(ITEM_TABLE_NAME, arrayOf(ITEM_ID, ITEM_NAME, ITEM_DESC), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val item =  Item(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_DESC))
            )
            items.add(item)
        }
        cursor.close()
        db.close()
        return items
    }

    fun addStoredItems(storedItem: StoredItem) {
        val values = ContentValues().apply {
            put(STOREDITEM_ITEM_ID, storedItem.item.id)
            put(STOREDITEM_STORE_ID, storedItem.store.id)
            put(STOREDITEM_PLACE, storedItem.place)
        }
        val db = writableDatabase
        db.insert(STOREDITEM_TABLE_NAME, null, values)
        db.close()
    }
    fun getStoredItems(): List<StoredItem> {
        val items =  getItems()
        val stores = getStores()
        val storedItems = ArrayList<StoredItem>()
        val db = readableDatabase
        val cursor = db.query(STOREDITEM_TABLE_NAME, arrayOf(STOREDITEM_ITEM_ID, STOREDITEM_STORE_ID, STOREDITEM_PLACE), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val storeId = cursor.getInt(cursor.getColumnIndexOrThrow(STOREDITEM_STORE_ID))
            val store = stores.first { it.id == storeId }
            val itemId = cursor.getInt(cursor.getColumnIndexOrThrow(STOREDITEM_ITEM_ID))
            val item = items.first { it.id == itemId }

            val storedItem =  StoredItem(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(STOREDITEM_ITEM_ID)),
                store = store,
                item = item,
                place =  cursor.getString(cursor.getColumnIndexOrThrow(STOREDITEM_PLACE)),
            )
            storedItems.add(storedItem)
        }
        cursor.close()
        db.close()
        return storedItems
    }
}