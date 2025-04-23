package com.example.myapplication22

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.domain.Item
import com.example.myapplication22.domain.Store
import com.example.myapplication22.domain.StoredItem
import com.example.myapplication22.persistence.StoreDatabase
import kotlin.concurrent.Volatile


class AddStoredItems : AppCompatActivity() {

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
        val saveButton: Button = findViewById(R.id.saveButtom)
        val backButton: Button = findViewById(R.id.backButton)

        val items = StoreDatabase.getDB().getItems()
        val stores = StoreDatabase.getDB().getStores()

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
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        backButton.setOnClickListener(){
            val bb = Intent(this, MainActivity::class.java)
            startActivity(bb)
        }
        saveButton.setOnClickListener(){
            val place = addPlace.text.toString().trim()

            if (place.isNotEmpty()){
                StoreDatabase.getDB().addStoredItems( StoredItem(selectedStore!!, selectedItem!!, place))
                Toast.makeText(this, "сохранено", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "добавьте место", Toast.LENGTH_SHORT).show()
        }



    }
}

