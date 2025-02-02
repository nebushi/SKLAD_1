package com.example.myapplication22

import android.R.layout
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.domain.Store
import com.example.myapplication22.persistence.StoreDatabase
import com.google.android.material.navigation.NavigationBarView


class StorageActivity : AppCompatActivity() {

    private lateinit var storeDb: StoreDatabase

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeDb = StoreDatabase(this)

        enableEdgeToEdge()
        setContentView(R.layout.activity_add_str)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bacceStr = findViewById<Button>(R.id.bbe)
        val finishh : Button = findViewById(R.id.finish)
        val MainNamestr :EditText= findViewById(R.id.mainNameStr)
        val deck: EditText = findViewById(R.id.DescStr)
        bacceStr.setOnClickListener{
            val bb = Intent(this, MainActivity::class.java)
            startActivity(bb)
        }
        finishh.setOnClickListener{
            val storageName = MainNamestr?.text.toString().trim()
            val storageAddress = deck?.text.toString().trim()
            storeDb.addStore(Store(storageName, storageAddress))
           when{
               storageAddress!=null  -> Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
               storageName!=null -> Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
               else -> Toast.makeText(this, "nothing to save", Toast.LENGTH_SHORT).show()
           }




            // get a cursor from the database with an "_id" field
            val c: Cursor = Store.rawQuery("SELECT id AS _id, name FROM store", null)


            // make an adapter from the cursor
            val from = arrayOf("name")
            val to = intArrayOf(R.id.storageList)
            val sca = SimpleCursorAdapter(this,android.R.layout.simple_spinner_item, c, from, to)


            // set layout for activated adapter
            sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


            // get xml file spinner and set adapter
            val spin : Spinner = findViewById(R.id.spinner33)
            spin.adapter = sca


            // set spinner listener to display the selected item id
            spin.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener() {
                fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Toast.makeText(this, "Selected ID=$id", Toast.LENGTH_LONG).show()
                }

                fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        }
    }
}