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

        val id = intent.getIntExtra("id",0)
        var database: myDatabase = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).fallbackToDestructiveMigration().build()
        GlobalScope.launch {
            DataObject.listExpense = database.edao().getExpense(id) as MutableList<ExpenseInfo>
        }

        add.setOnClickListener {
            val intent = Intent(this, CreateExpense::class.java)
            startActivity(intent)
        }
        setRecycler(id)
    }
    fun setRecycler(pos: Int){
        expense_list.adapter = ExpenseAdapter(DataObject.getAllExpenseData(pos))
        expense_list.layoutManager = LinearLayoutManager(this)
    }
}