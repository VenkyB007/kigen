package com.application.kigen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database: myDatabase = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).build()
        GlobalScope.launch {
            DataObject.listExpense = database.edao().getAllExpenses() as MutableList<ExpenseInfo>
        }
        add.setOnClickListener {
            val intent = Intent(this, CreateProfile::class.java)
            startActivityForResult(intent, 1)
        }
        deleteAllProfiles.setOnClickListener{
            DataObject.deleteAllProfiles()
            GlobalScope.launch {
                database.dao().deleteAll()
            }
            setRecycler()
        }
        setRecycler()

    }
    fun setRecycler() {
        profile_view.adapter = ProfileAdapter(DataObject.getAllData())
        profile_view.layoutManager = LinearLayoutManager(this)
    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            setRecycler()
        }
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }
}