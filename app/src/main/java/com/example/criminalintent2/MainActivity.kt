package com.example.criminalintent2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.criminalintent2.CrimeListFragment
import java.util.*
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() , CrimeListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, CrimeListFragment.newInstance())
            .commit()
    }

    override fun onCrimeSelected(crimeId: UUID) {
       //Log.d(TAG, " MainActivity.onCrimeSelected:$crimeId")
        val fragment = CrimeFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container ,fragment)
            .addToBackStack(null)
            .commit()
    }
}