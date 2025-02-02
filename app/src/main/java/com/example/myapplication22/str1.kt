package com.example.myapplication22

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.databinding.ActivityStr1Binding
import com.example.myapplication22.domain.Item
import com.example.myapplication22.persistence.StoreDatabase
import android.util.Log;


class str1 : AppCompatActivity() {

    private lateinit var storeDb: StoreDatabase

    @SuppressLint("MissingInflatedId")
    lateinit var binding: ActivityStr1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeDb = StoreDatabase(this)

        binding = ActivityStr1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_str1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bb2 = findViewById<TextView>(R.id.bb2)
        val MainNameo: EditText = findViewById(R.id.MainNameobj)
        val Numm: EditText = findViewById(R.id.numbers)
        val savee: Button = findViewById(R.id.saveB)
        bb2.setOnClickListener { view ->
            val ea2 = Intent(this, obj1::class.java)
            startActivity(ea2)
        }
        savee.setOnClickListener{
            val  itemName = MainNameo.text.toString().trim()
            val itemDeck = Numm.text.toString().trim()

            if (itemName.isNotEmpty()) {
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                storeDb.addItem(Item(itemName, itemDeck))
                Log.i("Items",
                    storeDb.getItems().map { "${it.id} , ${it.name}, ${it.description}" }.joinToString(";"))
            }

            else Toast.makeText(this, "nothing to save", Toast.LENGTH_SHORT).show()
        }

    }
}