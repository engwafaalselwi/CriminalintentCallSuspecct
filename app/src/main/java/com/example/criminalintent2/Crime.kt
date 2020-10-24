package com.example.criminalintent2

import android.widget.TimePicker
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity
data class Crime(@PrimaryKey val id:UUID = UUID.randomUUID(),
                 var title : String = " ",
                 var date : Date = Date(),

                // var requiresPolice:Boolean = false,
                 var isSolved :Boolean = false)
{

    }
