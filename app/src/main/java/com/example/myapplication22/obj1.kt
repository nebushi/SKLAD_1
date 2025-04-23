package com.example.myapplication22

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.persistence.StoreDatabase

class obj1 : AppCompatActivity() {

    private lateinit var storeDb: StoreDatabase

    @SuppressLint("MissingInflatedId","WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeDb = StoreDatabase(this)

        enableEdgeToEdge()
        setContentView(R.layout.activity_obj1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bac = findViewById<Button>(R.id.bake)
        val addObj = findViewById<Button>(R.id.addObj)
        val ItemList = findViewById<ListView>(R.id.itemList)
        val ITlist =  storeDb.getItems().map {
            "${it.name}, ${it.description}"
        }
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, ITlist)
        ItemList.adapter = adapter1


        bac.setOnClickListener { view ->
            val eaa2 = Intent(this, MainActivity::class.java)
            startActivity(eaa2)
        }
        addObj.setOnClickListener { view ->
            val eaa22 = Intent(this, str1::class.java)
            startActivity(eaa22)
        }
        ItemList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ItemsView::class.java)
            intent.putExtra("ITEM_POSITION", position)
            startActivity(intent)
        }
    }
}