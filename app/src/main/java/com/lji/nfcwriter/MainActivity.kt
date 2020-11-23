package com.lji.nfcwriter

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.FormatException
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var nfcAdapter: NfcAdapter
    var tag: WritableTag? = null
    var tagId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNFCAdapter()
        writeNDefMessage()
    }

    private fun writeNDefMessage() {
        val message = NfcUtils.prepareMessageToWrite("Hello NFC, this is my first tag!", this)
        val writeResult = tag!!.writeData(tagId!!, message)
        if (writeResult) {
            Log.e("Write successful","Hurrah")
        } else {
            Log.e("Write failed","Uff!")
        }
    }

    override fun onResume() {
        super.onResume()
        enableNFCForegroundDispatch()
    }

    private fun enableNFCForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
        }catch (ex : Exception){
            Log.e(javaClass.name,"Error enabling NFC foreground dispatch",ex)
        }
    }

    private fun initNFCAdapter() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = nfcManager.defaultAdapter
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tagFromIntent = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        try {
            tag = WritableTag(tagFromIntent!!)
        } catch (e: FormatException) {
            Log.e("Uff", "Unsupported tag tapped", e)
            return
        }
        tagId = tag!!.tagId
        Log.e(javaClass.name,"Tag tapped")

//        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
//            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
//            if (rawMsgs != null) {
//                onTagTapped(NfcUtils.getUID(intent), NfcUtils.getData(rawMsgs))
//            }
//        }
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    private fun disableNfcForegroundDispatch() {
        try {
            nfcAdapter.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Log.e(javaClass.name, "Error disabling NFC foreground dispatch", ex)
        }
    }

}