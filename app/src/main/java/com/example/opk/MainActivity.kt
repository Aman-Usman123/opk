package com.example.opk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CountdownAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }

    private inner class CountdownAdapter : RecyclerView.Adapter<CountdownViewHolder>() {

        private val startTimeList = listOf(
            TimeUnit.MINUTES.toMillis(5),
            TimeUnit.MINUTES.toMillis(10),
            TimeUnit.MINUTES.toMillis(15),
            TimeUnit.MINUTES.toMillis(20)
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountdownViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listitems, parent, false)
            return CountdownViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CountdownViewHolder, position: Int) {
            holder.bind(startTimeList[position])
        }

        override fun getItemCount(): Int {
            return startTimeList.size
        }

        override fun onViewRecycled(holder: CountdownViewHolder) {
            super.onViewRecycled(holder)
            holder.stopCountdown()
        }
    }
}
