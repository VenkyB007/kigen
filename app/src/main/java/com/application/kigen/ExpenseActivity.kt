package com.application.kigen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
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
        GlobalScope.launch {
            val listExpense = database.edao().getExpense(position) as MutableList<ExpenseInfo>
            setRecycler(listExpense)
        }

        add.setOnClickListener {
            val intent = Intent(this, CreateExpense::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
        deleteAllExpense.setOnClickListener{
            DataObject.deleteAllProfileExpenses(position)
            GlobalScope.launch {
                database.edao().deleteAllProfileExpense(position)
                val listExpense = database.edao().getExpense(position) as MutableList<ExpenseInfo>
                setRecycler(listExpense)
            }
        }
    }
    fun setRecycler(listExpense: List<ExpenseInfo>){
        val expenselist = findViewById<RecyclerView>(R.id.expense_list)
        expenselist.adapter = ExpenseAdapter(listExpense)
        expenselist.layoutManager = LinearLayoutManager(this@ExpenseActivity)
    }

}

interface ItemClickListener {
    fun onItemClick(position: Int)
}