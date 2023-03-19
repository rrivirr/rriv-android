package org.rriv.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import org.rriv.android.results.OutputScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) actionBar.hide()
        setContentView(R.layout.activity_main)
    }
    override fun onNewIntent(intent: Intent) {
        if ("android.hardware.usb.action.USB_DEVICE_ATTACHED" == intent.action) {
            val terminal: OutputScreenFragment? =
                supportFragmentManager.findFragmentByTag("terminal") as OutputScreenFragment?
            if (terminal != null) terminal.status("USB device detected")
        }
        super.onNewIntent(intent)
    }
}