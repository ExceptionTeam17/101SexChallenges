package com.exceptionteam17.a101sexchallenges

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.exceptionteam17.a101sexchallenges.helpers.Data
import com.exceptionteam17.a101sexchallenges.model.Challenge

class ChallengeActivity : AppCompatActivity() {

    private var id: Int? = null
    private var challenge: Challenge? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)
        id = intent.getIntExtra("id", 0)
        challenge = Data.list!![id!!]
    }
}
