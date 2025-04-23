package com.example.myapplication22

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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication22.domain.Item
import com.example.myapplication22.domain.Store
import com.example.myapplication22.domain.StoredItem
import com.example.myapplication22.persistence.StoreDatabase


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
        val finishButton: Button = findViewById(R.id.finish)
        val mainNameStr: EditText = findViewById(R.id.mainNameStr)
        val deck: EditText = findViewById(R.id.DescStr)

        bacceStr.setOnClickListener {
            val bb = Intent(this, MainActivity::class.java)
            startActivity(bb)
        }


        finishButton.setOnClickListener {
            val storageName = mainNameStr.text.toString().trim()
            val storageAddress = deck.text.toString().trim()
            if (storageName.isNotEmpty() and storageAddress.isNotEmpty() ) {
                val store = Store(storageName, storageAddress)
                storeDb.addStore(store)
                Toast.makeText(this, "сохранено", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "добавьте название", Toast.LENGTH_SHORT).show()

        }

    }
}
