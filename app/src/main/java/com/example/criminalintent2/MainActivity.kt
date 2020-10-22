package com.example.criminalintent2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.criminalintent2.CrimeListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, CrimeListFragment.newInstance())
            .commit()
    }
}