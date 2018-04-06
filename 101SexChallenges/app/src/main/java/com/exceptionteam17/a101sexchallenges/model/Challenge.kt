package com.exceptionteam17.a101sexchallenges.model

class Challenge (var id: Int, var text: String, var state: Int, var openDate: Long, var firstDone: Long, var comment: String, var isLoved: Boolean){
    companion object {
        const val NEW = 1
        const val DONE = 2
        const val LOVE = 3
        const val NEVER = 4
        const val NOTNOW = 5
        const val OPPEND = 6
    }
}