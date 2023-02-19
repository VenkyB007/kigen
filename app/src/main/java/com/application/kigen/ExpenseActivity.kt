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
import kotlinx.android.synthetic.main.expense_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExpenseActivity : AppCompatActivity() {
    private val profilePosition = intent?.getIntExtra("profilePosition",0) ?: 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)
        val profilePosition = intent.getIntExtra("position",0)

        val database: myDatabase = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).fallbackToDestructiveMigration().build()


        add.setOnClickListener {
            val intent = Intent(this, CreateExpense::class.java)
            intent.putExtra("profilePosition", profilePosition)
            startActivityForResult(intent, 1)

        }
        deleteAllExpense.setOnClickListener{

            DataObject.deleteAllProfileExpenses(profilePosition)
            GlobalScope.launch {
                database.edao().deleteAllProfileExpense(profilePosition)
            }
            setRecycler(profilePosition)
        }
        swipeToRefreshExpense.setOnRefreshListener{
            GlobalScope.launch {
                DataObject.listExpense = database.edao().getAllExpenses() as MutableList<ExpenseInfo>
            }

            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
            setRecycler(profilePosition)

            swipeToRefreshExpense.isRefreshing = false
        }
        setRecycler(profilePosition)
    }
    fun setRecycler(profilePosition: Int){
        expense_list.adapter = ExpenseAdapter(DataObject.getProfileExpense(profilePosition),profilePosition)
        expense_list.layoutManager = LinearLayoutManager(this@ExpenseActivity)

    }

}