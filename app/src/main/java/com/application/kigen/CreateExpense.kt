package com.application.kigen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_expense.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateExpense : AppCompatActivity() {
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_expense)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).build()
        val profileId = intent.getIntExtra("position", -1)

        ex_save_button.setOnClickListener {
            if (profileId != -1) {
                val name = create_expense.text.toString()
                val priceText = create_price.text.toString().trim()

                if (name.isNotEmpty() && priceText.isNotEmpty()) {
                    // Check that price is a valid number
                    val regex = Regex("[0-9]+")
                    if (!priceText.matches(regex)) {
                        // Display error toast message
                        Toast.makeText(
                            this,
                            "Please enter a valid price (numbers only)",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }

                    var price = priceText.toInt()
                    DataObject.setExpenseData(profileId, name, price.toString())
                    GlobalScope.launch {
                        database.edao().insertExpense(ExpenseEntity(0, profileId, name, price.toString()))
                    }
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }
}
