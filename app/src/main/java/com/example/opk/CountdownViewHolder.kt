package com.example.opk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit

class CountdownViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

    private var countDownTimer: CountDownTimer? = null
    private lateinit var sharedPreferences: SharedPreferences

    fun bind(startTime: Long) {
        stopCountdown()

        val intent = Intent(itemView.context, CountdownService::class.java)
        intent.putExtra(CountdownService.EXTRA_START_TIME, startTime)
        itemView.context.startService(intent)

        sharedPreferences = itemView.context.getSharedPreferences("CountdownPrefs", Context.MODE_PRIVATE)
        val timeLeft = sharedPreferences.getLong("timeLeft", startTime)
        startCountdown(timeLeft)
    }

    fun stopCountdown() {
        countDownTimer?.cancel()
    }

    private fun startCountdown(startTime: Long) {
        countDownTimer = object : CountDownTimer(startTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                timeTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timeTextView.text = "00:00"
            }
        }
        countDownTimer?.start()
    }
}
