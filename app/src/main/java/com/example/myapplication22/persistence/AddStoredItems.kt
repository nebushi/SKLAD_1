package com.example.myapplication22.persistence

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.MainActivity
import com.example.myapplication22.R
import com.example.myapplication22.domain.Item
import com.example.myapplication22.domain.Store
import com.example.myapplication22.domain.StoredItem
import kotlin.concurrent.Volatile

class AddStoredItems : AppCompatActivity() {
    private lateinit var storeDb: StoreDatabase

    @Volatile
    private var selectedItem: Item? = null
    @Volatile
    private var selectedStore : Store? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_stored_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addPlace: EditText = findViewById(R.id.addPlace)
        val itemSpinner: Spinner = findViewById(R.id.itemSpinner)
        val storeSpinner: Spinner = findViewById(R.id.storeSpinner)
        val selectedItemTextView: TextView = findViewById(R.id.itemView)
        val selectedStoreTextView: TextView = findViewById(R.id.storesView)
        val saveButton: Button = findViewById(R.id.saveButtom)
        val backButton: Button = findViewById(R.id.backButton)

        val items = storeDb.getItems()
        val stores = storeDb.getStores()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items.map { it.name }.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        itemSpinner.adapter = adapter

        val storeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stores.map { it.name }.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        storeSpinner.adapter = storeAdapter
        itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = items[position]
                selectedItemTextView.text = "Выбранный предмет: $selectedItem"
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        storeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStore = stores[position]
                selectedStoreTextView.text = "Выбранный склад: $selectedStore"
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        backButton.setOnClickListener(){
            val bb = Intent(this, MainActivity::class.java)
            startActivity(bb)
        }
        saveButton.setOnClickListener(){
            val place = addPlace?.text.toString()?.trim()
            if (selectedItem != null && selectedStore != null && place != null){
                storeDb.addStoredItems( StoredItem(selectedStore!!, selectedItem!!, place))
            }
        }
    }
}