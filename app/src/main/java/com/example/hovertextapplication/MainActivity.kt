package com.example.hovertextapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.hover.sdk.permissions.PermissionActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), Hover.DownloadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Hover.initialize(getApplicationContext(), this)


        permissions_button.setOnClickListener {
            val i = Intent(applicationContext, PermissionActivity::class.java)
            startActivityForResult(i, 0)
        }

        val button =
            findViewById<View>(R.id.action_button) as Button
        button.isEnabled = true
        button.setOnClickListener {
                val i = HoverParameters.Builder(this@MainActivity)
                    .request("83a57bf3")
                    .extra("account","00*****623")
                    .extra("amount","100")
                    .extra("phoneNumber","09025748018")
                    // Uncomment and add your variables if any
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


}

