package com.exceptionteam17.a101sexchallenges.helpers

import com.exceptionteam17.a101sexchallenges.model.Challenge

class Data private constructor(){
    companion object {
        var list: ArrayList<Challenge>? = null

        fun load (dataIn: ArrayList<Challenge>){
            list = dataIn
        }
    }
}