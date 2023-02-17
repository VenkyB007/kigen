package com.application.kigen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_update_expense.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateExpense: AppCompatActivity() {
    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_expense)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).build()
        val pos = intent.getIntExtra("profilePosition", -1)
        val epos = intent.getIntExtra("expensePosition",0)
        if (pos != -1) {
            val name = DataObject.getData(pos,epos).name
            val price = DataObject.getData(pos,epos).price
            update_expense_name.setText(name)
            update_price.setText(price)

            update_button.setOnClickListener{
                DataObject.updateData(
                    update_expense_name.text.toString(),
                    update_price.text.toString()
                )
                GlobalScope.launch {
                    database.edao().updateExpense(
                        ExpenseEntity(
                            epos + 1,
                            pos,
                            update_expense_name.text.toString(),
                            update_price.text.toString()
                        )
                    )
                }
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    fun myIntent() {
        val intent = Intent(this, ExpenseActivity::class.java)
        startActivity(intent)
    }
}