package com.application.kigen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
            val profileName = create_profile.text.toString().trim()
            if (profileName.isNotEmpty()) {
                if (DataObject.isProfileNameUnique(profileName)) {
                    DataObject.setProfileData(0, profileName)
                    GlobalScope.launch {
                        database.dao().insertProfile(Entity(0, profileName, 0))
                    }
                    val intent = Intent().apply {
                        putExtra("profileName", profileName)
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Profile \"$profileName\" already exists", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}