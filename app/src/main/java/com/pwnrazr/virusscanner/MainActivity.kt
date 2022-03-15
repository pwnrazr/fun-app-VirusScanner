package com.pwnrazr.virusscanner

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doingText: TextView = findViewById(R.id.doing_text)
        val virusAmtText: TextView = findViewById(R.id.virus_amount_text)
        val button: Button = findViewById(R.id.scan_button)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        var isScanning = false

        var randomNum = 0

        // Startup message
        val startUpMessage = AlertDialog.Builder(this)
        startUpMessage.setTitle("Welcome")
        startUpMessage.setMessage("Thank you for using Virus Scanner by Pwnrazr")
        startUpMessage.setPositiveButton("Okay") { dialog, which ->
        }
        startUpMessage.show()

        // Viruses found dialog
        val virusDialog = AlertDialog.Builder(this)
        virusDialog.setTitle("Virus detected!")
        virusDialog.setMessage("Viruses found on your device")
        virusDialog.setPositiveButton("Okay") { dialog, which ->
        }

        fun animateTextView(initialValue: Int, finalValue: Int, textview: TextView) {
            val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
            textview.text = "0"
            valueAnimator.duration = 1500 //ms
            valueAnimator.addUpdateListener { valueAnimator ->
                textview.text = valueAnimator.animatedValue.toString()
            }
            valueAnimator.start()
        }

        class SimpleRunnable: Runnable {
            override fun run() {
                while(!Thread.currentThread().isInterrupted && isScanning)
                {
                    if(virusAmtText.text == randomNum.toString())
                    {
                        Thread.currentThread().interrupt()
                        isScanning = false

                        doingText.text = "Scanning complete"
                        progressBar.visibility = View.INVISIBLE
                        Log.d("myApp","Stopped")
                        val mHandler = Handler(Looper.getMainLooper())

                    mHandler.post(Runnable {
                            virusDialog.show()
                        })
                    }
                    Log.d("myApp","Running")
                }
            }
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        button.setOnClickListener {
            if(!isScanning) {
                virusAmtText.text = "0"
                randomNum = (86..523).random()

                doingText.text = "Scanning device"
                progressBar.visibility = View.VISIBLE

                isScanning = true

                animateTextView(0, randomNum, virusAmtText)
                val threadWithRunnable = Thread(SimpleRunnable())
                threadWithRunnable.start()
            }
            else {
                Toast.makeText(applicationContext,"Scan ongoing",Toast.LENGTH_SHORT).show()
            }
        }
    }
}