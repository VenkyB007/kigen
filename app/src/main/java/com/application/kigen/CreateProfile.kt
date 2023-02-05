package com.application.kigen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateProfile: AppCompatActivity() {

    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).build()
        save_button.setOnClickListener {
            if (create_profile.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                var title = create_profile.text.toString()
                DataObject.setProfileData(0,title)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, 0))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}