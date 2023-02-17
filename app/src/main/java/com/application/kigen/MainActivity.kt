package com.application.kigen

import ProfileAdapter
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var backPressedTime = 0L
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
        swipeToRefresh.setOnRefreshListener{
            GlobalScope.launch {
                DataObject.listProfiles = database.dao().getTasks() as MutableList<ProfileInfo>
            }
            Log.v(DataObject.listProfiles.toString(),"d")
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
            setRecycler()

            swipeToRefresh.isRefreshing = false
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

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            moveTaskToBack(true)
            super.onBackPressed()
            finish()
        }else {
            Toast.makeText(applicationContext,"Press back again to exit app",Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

}