package com.example.criminalintent2

import android.content.ComponentCallbacks
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_of_serious_crime.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

class CrimeListFragment : Fragment () {

    interface Callbacks{
        fun onCrimeSelected(crimeId: UUID)

    }
    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel :CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks ?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

      companion object {
          fun newInstance():CrimeListFragment{
              return CrimeListFragment()
          }
      }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_crime_list,container,false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
           // udateUI()


        return view
       // return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        callbacks = null
    }
    private fun updateUI(crimes: List<Crime>) {

        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }




    private inner class CrimeViewHolder (view :View) :RecyclerView.ViewHolder(view),View.OnClickListener{
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        val solvedImageView :ImageView = itemView.findViewById(R.id.crime_solved)




        lateinit var crime : Crime

        init {
           itemView.setOnClickListener (this)
        }

        fun build(crime : Crime){
            this.crime = crime
            titleTextView.setText(crime.title)
            dateTextView.setText(SimpleDateFormat("EEE, MMM d, yyyy").format(this.crime.date).toString())
            solvedImageView.visibility = if(crime.isSolved){
                    View.VISIBLE
                } else {
                    View.GONE

            }
         
        }

        override fun onClick(v: View) {
            //Toast.makeText(context , "${crime.title}  presssed",Toast.LENGTH_LONG).show()
            callbacks ?.onCrimeSelected(crime.id)
        }

    }

    private inner  class CrimeAdapter(var crimes :List<Crime>) : ListAdapter<Crime,CrimeViewHolder>(CrimeDiffUtil())

    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
            //val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            val view : View = when(viewType){
                1->layoutInflater.inflate(R.layout.list_of_serious_crime, parent, false)
                else -> layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            }

           return CrimeViewHolder(view)
        }

        override fun getItemCount(): Int {
           return crimes.size
        }

        override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {


            holder.build(crimes[position])
        }

//        override fun getItemViewType(position: Int): Int {
//           when(crimes[position].requiresPolice){
//               true->return@getItemViewType 1
//               else->return@getItemViewType 0
//           }
//        }

    }
// 
    class CrimeDiffUtil : DiffUtil.ItemCallback<Crime>(){
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem.id == newItem.id
        }

    }
}