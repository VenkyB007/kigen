package com.application.kigen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_expense.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateExpense:AppCompatActivity() {
    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_expense)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).build()
        ex_save_button.setOnClickListener {
            if (create_expense.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_price.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                var name = create_expense.getText().toString()
                var price = create_price.text.toString()

                DataObject.setExpenseData(name, price)
                GlobalScope.launch {
                    database.edao().insertTask(ExpenseEntity(0, name, price))
                }
                val intent = Intent(this, ExpenseActivity::class.java)
                startActivity(intent)
            }
        }
    }
}