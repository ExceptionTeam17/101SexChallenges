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
import android.app.Dialog
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.chartboost.sdk.CBLocation
import com.chartboost.sdk.Chartboost
import com.exceptionteam17.a101sexchallenges.helpers.DatabaseHelper
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.dialog_close_layout.*
import kotlinx.android.synthetic.main.dialog_done_layout.*
import kotlinx.android.synthetic.main.dialog_skip_layout.*
import xyz.hanks.library.bang.SmallBangView
import java.lang.Exception


class ChallengeActivity : AppCompatActivity() {

    private var id: Int? = null
    private var db: DatabaseHelper? = null
    private var dialogSpip: Dialog? = null
    private var dialogClose: Dialog? = null
    private var dialogDone: Dialog? = null
//    private var adRequest: AdRequest? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

//        adRequest = AdRequest.Builder().build()
        Chartboost.onCreate(this)
        setContentView(R.layout.activity_challenge)
        try {
            supportActionBar!!.hide()
        } catch (ignored: Exception){}

        if(intent == null && id == null){
            exit()
        }
        id = intent.getIntExtra("id", 0)

        chal_text.text = Data.list?.get(id!!)?.text?: " "

        db = DatabaseHelper.getInstance(this)

        var calendar = Calendar.getInstance()

        like_heart.setOnClickListener {
                if (like_heart.isSelected) {
                    like_heart.isSelected = false
                    Data.list!![id!!].isLoved = like_heart.isSelected
                    db!!.updateIsLoved(Data.list!![id!!].id, like_heart.isSelected)
                } else {
                    like_heart.isSelected = true
                    Data.list!![id!!].isLoved = like_heart.isSelected
                    db!!.updateIsLoved(Data.list!![id!!].id, like_heart.isSelected)
                    like_heart.likeAnimation(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                }
            }

        if (Data.list!![id!!].isLoved){
            like_heart.isSelected = true
        }

        var date: String = ""
        if (Data.list!![id!!].openDate == 0L){
            date = calendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                    (calendar.get(Calendar.MONTH)+1).toString() + "." +
                    calendar.get(Calendar.YEAR).toString() + " / " +
                    calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    (if(calendar.get(Calendar.MINUTE) < 10) "0" else "") +
                            calendar.get(Calendar.MINUTE).toString()
            Data.list!![id!!].openDate = calendar.timeInMillis
            db!!.updateOpenDate(Data.list!![id!!].id, calendar.timeInMillis)
            Data.list!![id!!].state = Challenge.OPPEND
            db!!.updateState(Data.list!![id!!].id, Challenge.OPPEND)

        } else {
            calendar.timeInMillis = Data.list!![id!!].openDate
            date = calendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                    (calendar.get(Calendar.MONTH)+1).toString() + "." +
                    calendar.get(Calendar.YEAR).toString() + " / " +
                    calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    (if(calendar.get(Calendar.MINUTE) < 10) "0" else "") +
                            calendar.get(Calendar.MINUTE).toString()
        }
        chal_text_open_data.text = getString(R.string.first_time_open, date)

        if (Data.list!![id!!].firstDone == 0L){
            date = "never"

        } else {
            calendar.timeInMillis = Data.list!![id!!].firstDone
            date = calendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                    (calendar.get(Calendar.MONTH)+1).toString() + "." +
                    calendar.get(Calendar.YEAR).toString() + " / " +
                    calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    (if(calendar.get(Calendar.MINUTE) < 10) "0" else "") +
                            calendar.get(Calendar.MINUTE).toString()
        }
        chal_text_close_data.text = getString(R.string.first_time_done, date)

        chal_text_comment.text = getString(R.string.comment, Data.list!![id!!].comment)

        chal_number.text = getString(R.string.challenge, Data.list!![id!!].id)

        dialogSpip = Dialog(this, R.style.Theme_Dialog)
        dialogSpip!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialogSpip!!.setCancelable(false)
        dialogSpip!!.setContentView(R.layout.dialog_skip_layout)

        dialogClose = Dialog(this, R.style.Theme_Dialog)
        dialogClose!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogClose!!.setCancelable(false)
        dialogClose!!.setContentView(R.layout.dialog_close_layout)

        dialogDone = Dialog(this, R.style.Theme_Dialog)
        dialogDone!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialogDone!!.setCancelable(false)
        dialogDone!!.setContentView(R.layout.dialog_done_layout)

        chal_btn_skip.setOnClickListener {
            dialogSpip!!.di_skip_never.setOnClickListener {
                var str: String = dialogSpip!!.di_skip_edit.text.toString()
                if(str.isEmpty()){
                    str = " "
                }
                Data.list!![id!!].comment = str
                if(Data.list!![id!!].state != Challenge.DONE && Data.list!![id!!].state != Challenge.LOVE) {
                    Data.list!![id!!].state = Challenge.NEVER
                    db!!.updateState(Data.list!![id!!].id, Challenge.NEVER)
                }
                db!!.updateComment(Data.list!![id!!].id, str)
                setResult(Activity.RESULT_OK)
                dialogSpip!!.dismiss()
                finish()
            }

            dialogSpip!!.di_skip_edit.setText(Data.list!![id!!].comment.trim())

            dialogSpip!!.di_skip_not_now.setOnClickListener {
                var str: String = dialogSpip!!.di_skip_edit.text.toString()
                if(str.isEmpty()){
                    str = " "
                }
                Data.list!![id!!].comment = str
                if(Data.list!![id!!].state != Challenge.DONE && Data.list!![id!!].state != Challenge.LOVE) {
                    Data.list!![id!!].state = Challenge.NOTNOW
                    db!!.updateState(Data.list!![id!!].id, Challenge.NOTNOW)
                }
                db!!.updateComment(Data.list!![id!!].id, str)
                setResult(Activity.RESULT_OK)
                dialogSpip!!.dismiss()
                finish()
            }

            dialogSpip!!.show()
        }

        chal_btn_close.setOnClickListener {
            exit()
        }

        chal_btn_done.setOnClickListener {
            dialogDone!!.di_done_edit.setText(Data.list!![id!!].comment.trim())

            dialogDone!!.di_done_like_heart.setOnClickListener {
                if (dialogDone!!.di_done_like_heart.isSelected) {
                    dialogDone!!.di_done_like_heart.isSelected = false
                } else {
                    dialogDone!!.di_done_like_heart.isSelected = true
                    dialogDone!!.di_done_like_heart.likeAnimation(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                        }
                    })
                }
            }

            if(Data.list!![id!!].isLoved){
                dialogDone!!.di_done_like_heart.isSelected = true
            }

            dialogDone!!.di_done_back.setOnClickListener {
                dialogDone!!.dismiss()
            }

            dialogDone!!.di_done_ok.setOnClickListener {
                var str: String = dialogDone!!.di_done_edit.text.toString()
                if(str.isEmpty()){
                    str = " "
                }
                Data.list!![id!!].comment = str
                if(Data.list!![id!!].state != Challenge.DONE && Data.list!![id!!].state != Challenge.LOVE) {
                    Data.list!![id!!].state = Challenge.DONE
                    db!!.updateState(Data.list!![id!!].id, Challenge.DONE)
                    ShPrefs.addToProgress(this@ChallengeActivity)
                }
                db!!.updateComment(Data.list!![id!!].id, str)
                if(Data.list!![id!!].firstDone == 0L) {
                    var time = Calendar.getInstance().timeInMillis
                    Data.list!![id!!].firstDone = time
                    db!!.updateFirstDone(Data.list!![id!!].id, time)
                }

                Data.list!![id!!].isLoved = dialogDone!!.di_done_like_heart.isSelected
                db!!.updateIsLoved(Data.list!![id!!].id, dialogDone!!.di_done_like_heart.isSelected)

                setResult(Activity.RESULT_OK)
                dialogDone!!.dismiss()
                finish()
            }

            dialogDone!!.show()
        }
//        adViewC.loadAd(adRequest)
        Chartboost.setAutoCacheAds(true)
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT)
        Handler().postDelayed({
            try {
                if (Chartboost.hasInterstitial(CBLocation.LOCATION_DEFAULT)) {
                    Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT)
                } else {
                    Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT)
                }
            }catch (ignored: Throwable){}
        }, 10000)
    }

    override  fun onBackPressed() {
        if (Chartboost.onBackPressed())
            return
        exit()
    }

    private fun exit(){
//        dialogClose!!.di_close_no.setOnClickListener {
//            dialogClose!!.dismiss()
//        }
//
//        dialogClose!!.di_close_yes.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
//            dialogClose!!.dismiss()
            finish()
//        }
//
//        dialogClose!!.show()

    }

    public override fun onStart() {
        super.onStart()
        Chartboost.onStart(this)
    }

    override fun onResume() {
        super.onResume()
        Chartboost.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        Chartboost.onPause(this)

    }

    public override fun onStop() {
        super.onStop()
        Chartboost.onStop(this)
    }

    override fun onDestroy() {
        Chartboost.onDestroy(this)
        super.onDestroy()
    }
}
