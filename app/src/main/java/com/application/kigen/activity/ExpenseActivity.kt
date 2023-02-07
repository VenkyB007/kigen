package com.application.kigen.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.application.kigen.R
import com.application.kigen.adapter.ExpenseAdapter
import com.application.kigen.database.DataObject
import com.application.kigen.database.myDatabase
import com.application.kigen.model.ExpenseInfo
import kotlinx.android.synthetic.main.activity_expense_list.*
import kotlinx.android.synthetic.main.activity_main.add
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class ExpenseActivity : AppCompatActivity() {
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    var position: Int = -1
    var database: myDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        activityResultLauncher = registerForActivityResult(StartActivityForResult()) { activityResult: ActivityResult -> this.registerCallBackMethod(activityResult) }

        position = intent.getIntExtra("position",0)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).fallbackToDestructiveMigration().build()
        GlobalScope.launch {
            val listExpense = database!!.edao().getExpense(position) as MutableList<ExpenseInfo>
            setRecycler(listExpense)
        }

        add.setOnClickListener {
            val intent = Intent(this, CreateExpense::class.java)
            intent.putExtra("position", position)
            activityResultLauncher!!.launch(intent)
        }
        deleteAllExpense.setOnClickListener{
            DataObject.deleteAllProfileExpenses(position)
            GlobalScope.launch {
                database!!.edao().deleteAllProfileExpense(position)
                val listExpense = database!!.edao().getExpense(position) as MutableList<ExpenseInfo>
                setRecycler(listExpense)
            }
        }
    }

    private fun registerCallBackMethod(activityResult: ActivityResult) {
        val resultCode = activityResult.resultCode
        if (resultCode == 2) {
            // Refresh data
            val listExpense = database?.edao()?.getExpense(position) as MutableList<ExpenseInfo>
            setRecycler(listExpense)
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