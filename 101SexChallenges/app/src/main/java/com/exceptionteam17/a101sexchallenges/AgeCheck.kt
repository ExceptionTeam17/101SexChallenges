package com.exceptionteam17.a101sexchallenges

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_age_check.*
import kotlinx.android.synthetic.main.dialog_are_sure.*

class AgeCheck : AppCompatActivity() {

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_check)
        supportActionBar!!.hide()

        dialog = Dialog(this, R.style.Theme_Dialog)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.dialog_are_sure)

        btn_age_no.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        btn_age_yes.setOnClickListener {
            dialog!!.btn_age_dialog_no.setOnClickListener {
                dialog!!.dismiss()
            }

            dialog!!.btn_age_dialog_yes.setOnClickListener {
                setResult(Activity.RESULT_OK)
                dialog!!.dismiss()
                finish()
            }
            dialog!!.show()
        }
    }
}
