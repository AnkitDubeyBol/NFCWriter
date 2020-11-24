package com.lji.nfcwriter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.lji.nfcwriter.databinding.ActivityReaderBinding

class ReaderActivity : AppCompatActivity() {

    lateinit var binding : ActivityReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val msg = NFCUtils.retrieveNFCMessage(this.intent)
        binding.tv.text= msg
    }
}