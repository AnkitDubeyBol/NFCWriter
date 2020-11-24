package com.lji.nfcwriter

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lji.nfcwriter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val nfcAdapter : NfcAdapter by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.saveBtn.setOnClickListener {
            Toast.makeText(this, "Touch NFC tag to save data!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val title =  binding.titleTv.text.toString()
        if(title.isBlank()){
            binding.root.errToast("Enter title please!")
            return
        }

        val desc = binding.noteTv.text.toString()
        if(desc.isEmpty()){
            binding.root.errToast("Description is mandatory!")
            return
        }

        val data = Json{
            "title" to title
            "desc" to desc
        }

        val messageWrittenSuccessfully = NFCUtils.createNFCMessage(data.toString(), intent)

        if(messageWrittenSuccessfully)
            binding.root.successToast("Successful Written to Tag")
        else
            binding.root.errToast("Something When wrong Try Again")
        
    }

    override fun onResume() {
        super.onResume()
        NFCUtils.enableNFCInForeground(nfcAdapter, this, javaClass)
    }

    override fun onPause() {
        super.onPause()
        NFCUtils.disableNFCInForeground(nfcAdapter, this)
    }

}