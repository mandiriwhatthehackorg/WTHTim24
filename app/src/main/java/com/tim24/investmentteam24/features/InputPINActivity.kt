package com.tim24.investmentteam24.features

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.tim24.investmentteam24.R
import kotlinx.android.synthetic.main.activity_input_pin.*

class InputPINActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pin)
        txtPinEntry.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }
        txtPinEntry.focus()
        txtPinEntry.setOnPinEnteredListener {
            val returnIntent = Intent()
            var result = false
            if (it.toString() == "123456") {
                setResult(Activity.RESULT_OK, returnIntent)
                result = true
            }
            returnIntent.putExtra("result", result)
            finish()
        }
    }
}
