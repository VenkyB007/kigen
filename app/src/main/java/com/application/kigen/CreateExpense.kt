package com.application.kigen

import android.app.Activity
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
        val profileId = intent.getIntExtra("position", -1)

        ex_save_button.setOnClickListener {
            if (profileId!=-1){
                if (create_expense.text.toString().trim { it <= ' ' }.isNotEmpty()
                    && create_price.text.toString().trim { it <= ' ' }.isNotEmpty()
                ) {
                    val name = create_expense.text.toString()
                    val price = create_price.text.toString()

                    DataObject.setExpenseData(profileId,name, price)
                    GlobalScope.launch {
                        database.edao().insertTask(ExpenseEntity(0,profileId, name, price))
                    }
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }
}