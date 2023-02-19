package com.application.kigen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_expense_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.add
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.FieldPosition

class ExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)
        val position = intent.getIntExtra("position",0)

        val database: myDatabase = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).fallbackToDestructiveMigration().build()


        add.setOnClickListener {
            val intent = Intent(this, CreateExpense::class.java)
            intent.putExtra("position", position)
            startActivityForResult(intent, 1)

        }
        deleteAllExpense.setOnClickListener{

            DataObject.deleteAllProfileExpenses(position)
            GlobalScope.launch {
                database.edao().deleteAllProfileExpense(position)
            }
            setRecycler(position)
        }
        swipeToRefreshExpense.setOnRefreshListener{
            GlobalScope.launch {
                DataObject.listExpense = database.edao().getAllExpenses() as MutableList<ExpenseInfo>
            }

            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
            setRecycler(position)

            swipeToRefreshExpense.isRefreshing = false
        }
        setRecycler(position)
    }
    fun setRecycler(position: Int){
        expense_list.adapter = ExpenseAdapter(DataObject.getProfileExpense(position),position)
        expense_list.layoutManager = LinearLayoutManager(this@ExpenseActivity)

    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val profileId = data?.getIntExtra("position", 0)?:0
            setRecycler(profileId)
        }
    }
}