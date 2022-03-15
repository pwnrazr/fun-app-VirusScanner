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

        fun animateTextView(initialValue: Int, finalValue: Int, textview: TextView) {
            val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
            textview.text = "0"
            valueAnimator.duration = 1500 //ms
            valueAnimator.addUpdateListener { valueAnimator ->
                textview.text = valueAnimator.animatedValue.toString()
            }
            valueAnimator.start()
        }

        // Startup message
        val startUpMessage = AlertDialog.Builder(this)
        startUpMessage.setTitle("Welcome")
        startUpMessage.setMessage("Thank you for using Virus Scanner by Pwnrazr")
        startUpMessage.setPositiveButton("Okay") { dialog, which ->
        }
        startUpMessage.show()

        // Success dialog
        val successDialog = AlertDialog.Builder(this)
        successDialog.setTitle("Success")
        successDialog.setMessage("Viruses successfully deleted!")
        successDialog.setPositiveButton("More kishes \uD83D\uDE18\uD83D\uDE18\uD83D\uDE18") { dialog, which ->
        }

        class RunnableSuccess: Runnable {
            override fun run() {
                while(!Thread.currentThread().isInterrupted)
                {
                    if(virusAmtText.text == "0")
                    {
                        Thread.currentThread().interrupt()
                        doingText.text = "I love you Alya\n\uD83D\uDE18❤"
                        Log.d("virusScanner","Stopped Thread check success")
                        val mHandler = Handler(Looper.getMainLooper())

                        mHandler.post(Runnable {
                            successDialog.show()
                        })
                    }
                    Log.d("virusScanner","Running Thread check success")
                }
            }
        }

        fun deleteVirus() {
            doingText.text = "Deleting viruses\nfor my ayang"
            animateTextView(randomNum, 0, virusAmtText)
            val successThread = Thread(RunnableSuccess())
            successThread.start()
        }

        // Viruses found dialog
        val virusDialog = AlertDialog.Builder(this)
        virusDialog.setTitle("Virus detected!")
        virusDialog.setMessage("Viruses found on your device!\n" +
                "To remove viruses you must give kishes to Amir")
        virusDialog.setPositiveButton("I agree \uD83D\uDE18") { dialog, which ->
            deleteVirus()
        }
        virusDialog.setNegativeButton("I agree \uD83D\uDE18") { dialog, which ->
            deleteVirus()
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
                        Log.d("virusScanner","Stopped Thread increase value")
                        val mHandler = Handler(Looper.getMainLooper())

                    mHandler.post(Runnable {
                            virusDialog.show()
                        })
                    }
                    Log.d("virusScanner","Running Thread increase value")
                }
            }
        }

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