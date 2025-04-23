package com.example.myapplication22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.persistence.StoreDatabase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        StoreDatabase.init(this)

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.i("StoredItems",
            StoreDatabase.getDB().getStoredItems()
                .joinToString("\n") { "${it.store.name} - ${it.store.address} : ${it.item.name} --> ${it.place}" }
        )

        val obj = findViewById<Button>(R.id.obej)
        val plusS: Button = findViewById(R.id.plusStr)
        val storageList = findViewById<ListView>(R.id.storageList)
        val addStoredItemButton: Button = findViewById(R.id.addStoredItemButton)
        val storeList =  StoreDatabase.getDB().getStores().map {
            "склад ${it.name}, адрес: ${it.address}"
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, storeList)
        storageList.adapter = adapter



        obj.setOnClickListener { view ->
            val eae = Intent(this, obj1::class.java)
                startActivity(eae)
        }
        plusS.setOnClickListener { view ->
            val ea2e = Intent(this, StorageActivity::class.java)
            startActivity(ea2e)
        }
        addStoredItemButton.setOnClickListener { view ->
            val intent  = Intent(this, AddStoredItems::class.java)
            startActivity(intent)

        }

        storageList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, StorageView::class.java)
            intent.putExtra("ITEM_POSITION", position)
            startActivity(intent)
        }


    }
}
