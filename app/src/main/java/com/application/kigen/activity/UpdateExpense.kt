package com.application.kigen.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.application.kigen.database.DataObject
import com.application.kigen.ExpenseEntity
import com.application.kigen.R
import com.application.kigen.database.myDatabase
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
        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val name = DataObject.getData(pos).name
            val price = DataObject.getData(pos).price
            update_expense_name.setText(name)
            update_price.setText(price)

            delete_button.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.edao().deleteTask(
                        ExpenseEntity(
                            pos + 1,
                            pos,
                            update_expense_name.text.toString(),
                            update_price.text.toString()
                        )
                    )
                }
                myIntent()
            }

            update_button.setOnClickListener {
                DataObject.updateData(
                    pos,
                    update_expense_name.text.toString(),
                    update_price.toString()
                )
                GlobalScope.launch {
                    database.edao().updateTask(
                        ExpenseEntity(
                            pos + 1,
                            pos,
                            update_expense_name.text.toString(),
                            update_price.text.toString()
                        )
                    )
                }
                myIntent()
            }

        }
    }

    fun myIntent() {
        val intent = Intent(this, ExpenseEntity::class.java)
        startActivity(intent)
    }
}