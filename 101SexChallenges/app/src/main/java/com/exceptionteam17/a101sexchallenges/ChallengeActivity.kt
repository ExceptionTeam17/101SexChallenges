package com.exceptionteam17.a101sexchallenges

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.exceptionteam17.a101sexchallenges.helpers.Data
import com.exceptionteam17.a101sexchallenges.helpers.ShPrefs
import com.exceptionteam17.a101sexchallenges.model.Challenge
import kotlinx.android.synthetic.main.activity_challenge.*
import java.text.SimpleDateFormat
import java.util.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import xyz.hanks.library.bang.SmallBangView


class ChallengeActivity : AppCompatActivity() {

    private var id: Int? = null
    private var challenge: Challenge? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)
        supportActionBar!!.hide()

        id = intent.getIntExtra("id", 0)
        challenge = Data.list!![id!!]
        chal_text.text = challenge!!.text

        var calendar = Calendar.getInstance()

        like_heart.setOnClickListener {
                if (like_heart.isSelected) {
                    like_heart.isSelected = false
                } else {
                    like_heart.isSelected = true
                    like_heart.likeAnimation(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                }
            }


        var date: String = ""
        if (challenge!!.openDate == 0L){
            date = calendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                    (calendar.get(Calendar.MONTH)+1).toString() + "." +
                    calendar.get(Calendar.YEAR).toString() + " / " +
                    calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    calendar.get(Calendar.MINUTE).toString()
            challenge!!.openDate = calendar.timeInMillis
            //TODO add to base
        } else {
            calendar.timeInMillis = challenge!!.openDate
            date = calendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                    (calendar.get(Calendar.MONTH)+1).toString() + "." +
                    calendar.get(Calendar.YEAR).toString() + " / " +
                    calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    if(calendar.get(Calendar.MINUTE) < 10) "0" else "" +
                            calendar.get(Calendar.MINUTE).toString()
        }
        chal_text_open_data.text = getString(R.string.first_time_open, date)

        if (challenge!!.firstDone == 0L){
            date = "never"

        } else {
            calendar.timeInMillis = challenge!!.firstDone
            date = calendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                    (calendar.get(Calendar.MONTH)+1).toString() + "." +
                    calendar.get(Calendar.YEAR).toString() + " / " +
                    calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    if(calendar.get(Calendar.MINUTE) < 10) "0" else "" +
                            calendar.get(Calendar.MINUTE).toString()
        }
        chal_text_close_data.text = getString(R.string.first_time_done, date)

        chal_text_comment.text = getString(R.string.comment, challenge!!.comment)
    }

    override  fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
