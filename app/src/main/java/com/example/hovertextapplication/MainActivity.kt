package com.example.hovertextapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.hover.sdk.permissions.PermissionActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), Hover.DownloadListener {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Hover.initialize(applicationContext, this)

        permissions_button.setOnClickListener {
            val i = Intent(applicationContext, PermissionActivity::class.java)
            startActivityForResult(i, 0)
        }

        val button =
            findViewById<View>(R.id.action_button) as Button
        button.isEnabled = true
        button.setOnClickListener {
                val i = HoverParameters.Builder(this@MainActivity)
                    .request("ddd43c9c")
                    .buildIntent()
                startActivityForResult(i, 0)
        }
    }

    override fun onSuccess(p0: ArrayList<HoverAction>?) {
        //		Toast.makeText(this, "Error while attempting to download actions, see logcat for error", Toast.LENGTH_LONG).show();
        //		Toast.makeText(this, "Successfully downloaded " + actions.size() + " actions", Toast.LENGTH_LONG).show();
        if (p0 != null) {
            Log.d("Success Message", "Successfully downloaded " + p0.size + " actions")
        }
    }
    override fun onError(p0: String?) {
        Log.e("Error Message",p0.toString())
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val sessionTextArr = data?.getStringArrayExtra("session_messages")
            val check = sessionTextArr?.get(sessionTextArr.size - 1)
            val myTex = findViewById<View>(R.id.text) as TextView
            myTex.text = check
            val uuid = data?.getStringExtra("uuid")
            Log.e(TAG, "Return array: $check")
            Log.e(TAG, "Return uuid: $uuid")
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Error: " + data?.getStringExtra("error"), Toast.LENGTH_LONG).show()
        }
    }

}

