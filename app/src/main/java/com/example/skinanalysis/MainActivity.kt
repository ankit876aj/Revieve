package com.example.skinanalysis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skinanalysis.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment())
                .commitNow()
        }
    }
}