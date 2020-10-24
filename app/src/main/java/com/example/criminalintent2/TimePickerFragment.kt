package com.example.criminalintent2

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle

import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*


private const val ARG_TIME = "time"
class TimePickerFragment:DialogFragment(){
    interface Callbacks {
        fun onTimeSelected(time: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calander = Calendar.getInstance()
        val TimeListener = TimePickerDialog.OnTimeSetListener {
                _: TimePicker, hours: Int, minutes: Int->
            calander.set(Calendar.HOUR_OF_DAY , hours)
            calander.set(Calendar.MINUTE , minutes)
            val resultTime =calander.time
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onTimeSelected(resultTime)
            }
        }
        val times = arguments?.getSerializable(ARG_TIME) as Date
        calander.time = times
        val hours=calander.get(Calendar.HOUR)
        val minutes=calander.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(),TimeListener,hours,minutes,true)
        //
    }
    companion object {
        fun newInstance(time : Date): TimePickerFragment{
            val args = Bundle().apply {
                putSerializable(ARG_TIME,time )
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}