package com.exceptionteam17.a101sexchallenges.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.exceptionteam17.a101sexchallenges.model.Challenge

class DatabaseHelper private constructor(context: Context):
        SQLiteOpenHelper(context, "challengesSex101.db", null, 1) {

    companion object {

        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    private val TABLE_USERS_SCORES = "users_scores"

    private val T_USERS_SCORES_COL_1 = "id"
    private val T_USERS_SCORES_COL_2 = "text"
    private val T_USERS_SCORES_COL_3 = "state"
    private val T_USERS_SCORES_COL_4 = "open_date"
    private val T_USERS_SCORES_COL_5 = "first_done"
    private val T_USERS_SCORES_COL_6 = "comment"
    private val T_USERS_SCORES_COL_7 = "loved"


    private val USERS_SCORES_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS_SCORES +
            " (" + T_USERS_SCORES_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            T_USERS_SCORES_COL_2 + " TEXT, " +
            T_USERS_SCORES_COL_3 + " INTEGER, " +
            T_USERS_SCORES_COL_4 + " INTEGER, " +
            T_USERS_SCORES_COL_5 + " INTEGER, " +
            T_USERS_SCORES_COL_6 + " TEXT, " +
            T_USERS_SCORES_COL_7 + " INTEGER);"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(USERS_SCORES_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //        String drop = "DROP TABLE " + USERS_SCORES_TABLE_CREATE + ";";
        //        db.execSQL(drop);
        onCreate(db)
    }

    fun addOnFirstRun() {
        val db = this.writableDatabase
        val data = listOf<String>(
                "It's time for erotic kissing! Kiss your partner very erotic.",
                "Do some normal sex - as you like it!",
                "Use some flavored lube. Yummy!",
                "Make long eye contact during sex.",
                "Give her oral.",
                "It's stripteeees time!",
                "Be more vocal during sex.",
                "Make a hickie / bite.",
                "Eat food/cream from his body.",
                "Make sex with clothes.",
                "Masturbate each other until orgasm.",
                "Give him oral. Try wearing bright red lipstick as your suck your partner’s cock.",
                "Let him dominate.",
                "Eat food/cream from her body.",
                "It's play time! Use toys on her.",
                "It's roleplay time. (Use costumes, or only your imagination) ",
                "Slap/spank her during sex.",
                "Anally finger her.",
                "Wear a dog collar with a leash. ",
                "Wear stockings and high heels during sex.",
                "It's 69 time!",
                "Let her dominate.",
                "Play some romantic music and have some very slow and romantic sex",
                "Slap/spank him during sex.",
                "Wake him up with sex or oral sex.",
                "Use ball gag.",
                "Have long teasing and foreplay session, focus on foreplay.",
                "Give him a sensual massage. Massage first, sex after!",
                "Make a movie and/or pictures",
                "Masturbate in front of each other until orgasm.",
                "wake her up with sex or oral sex.",
                "Watch porn together, do the same.",
                "Talk dirty during sex.",
                "Give her a sensual massage. Massage first, sex after!",
                "Have sex while on period.",
                "Make some roughter sex.",
                "It's play time! Use toys on him.",
                "Analingus. Try it! You will like it!",
                "Anal sex. Better use some lube!",
                "Sex in the woods or in a park.",
                "Anally finger him. You can try to find his prostate and massage it until he comes.",
                "Sex in a car. Old but great!",
                "Sex in the shower.",
                "Use cock ring.",
                "It's pegging time! Pegg him.",
                "Have sex in front of an outward facing window.",
                "DP her. (Use toy)",
                "It's play time! Use toys on each other.",
                "Go to strip club together.",
                "Try a new position.",
                "It's quickie time. Maybe at work?",
                "Sex in the kitchen.",
                "It's quiet sex time! Do not make a sound.",
                "Tie up sex - tie him up!",
                "Sex in living room.",
                "It's blindfolded sex time! Blindfold him.",
                "Wait on the bed for him, wearing nothing at all.",
                "Have sex, but not in the bedroom!",
                "It's morning sex time!",
                "Oral only! No penetration!",
                "Do it all night. :)",
                "Use ice cubes.",
                "Sex on the couch.",
                "Tie up sex - tie her up!",
                "Make out on a rooftop or under the stars",
                "Tell/ask sex fantasy and do it.",
                "It's blindfolded sex time! Blindfold her.",
                "Suck on your lover’s balls while he jacks off.",
                "Pick up a copy of the Kamasutra and try unique sex positions that both of you might enjoy.",
                "Get naughty on the beach with each other",
                "He is on the top.",
                "Grope or make out in transportation, be it on a flight, a train or an overnight bus.",
                "Finger her under a restaurant table. Just make sure the tablecloth’s big enough to cover your indecent act in public.",
                "She is on the top.",
                "Scratch each other and use your nails. Sometimes, pain can bring more pleasure.",
                "Have sex in a weird place.",
                "Have drunk sex after a wild night of partying.",
                "Use a double dildo with your man and penetrate him while getting penetrated yourself.",
                "Sex at the party or in club.",
                "It's shower time, but golden shower.",
                "Spank her.",
                "Cross dress in each other’s clothes.",
                "Do it in missionary.",
                "Use handcuffs.",
                "Sex on table - put her on.",
                "It's doggystyle time!",
                "Do it Rider. If Rider is boring for you, try Ape position",
                "It's light Bondage time.",
                "Use some oil/losion and lube. Make it slippery!",
                "Pegg him, then make anal.",
                "It's hand job time.",
                "It's doggystyle time again, but make it anal!",
                "Spank him.",
                "Try deep throat!",
                "It's titjob time. Slide his penis between her breasts and have sex that way.",
                "It's time to sit on his face.",
                "Anal again. This time only anal!",
                "Go to a sex shop together and choose something new.",
                "Have sex in the same room with another couple and watch each other. Change partners if you want!",
                "Do it on a hotel balcony.",
                "Stay naked at home all day and have sex as often as possible.")
        val contentValues = ContentValues()

        for(text: String in data){
            contentValues.put(T_USERS_SCORES_COL_2, text)
            contentValues.put(T_USERS_SCORES_COL_3, Challenge.NEW)
            contentValues.put(T_USERS_SCORES_COL_4, 0)
            contentValues.put(T_USERS_SCORES_COL_5, 0)
            contentValues.put(T_USERS_SCORES_COL_6, " ")
            contentValues.put(T_USERS_SCORES_COL_7, 0)
            db.insert(TABLE_USERS_SCORES, null, contentValues);
            contentValues.clear()
        }
    }

    fun getData() : ArrayList<Challenge>{
        var data : ArrayList<Challenge> = ArrayList()
        val db = this.writableDatabase
        val myRawQuery = "SELECT * FROM $TABLE_USERS_SCORES"
        val c = db.rawQuery(myRawQuery, null)
        if (c.count == 0) {
            return data
        }

        c.moveToFirst()
        do{
            data.add(Challenge(c.getInt(0), c.getString(1), c.getInt(2),
                    c.getLong(3), c.getLong(4), c.getString(5), c.getInt(6) == 1))
        } while(c.moveToNext())
        c.close()
        return data
    }

    fun updateState(id: Int, state: Int){
        val db = this.writableDatabase
        val update = "UPDATE $TABLE_USERS_SCORES SET $T_USERS_SCORES_COL_3 = $state WHERE $T_USERS_SCORES_COL_1 = \"$id\""
        db.execSQL(update)
    }

    fun updateOpenDate(id: Int, date: Long){
        val db = this.writableDatabase
        val update = "UPDATE $TABLE_USERS_SCORES SET $T_USERS_SCORES_COL_4 = $date WHERE $T_USERS_SCORES_COL_1 = \"$id\""
        db.execSQL(update)
    }


    fun updateFirstDone(id: Int, date: Long){
        val db = this.writableDatabase
        val update = "UPDATE $TABLE_USERS_SCORES SET $T_USERS_SCORES_COL_5 = $date WHERE $T_USERS_SCORES_COL_1 = \"$id\""
        db.execSQL(update)
    }

    fun updateComment(id: Int, comment: String){
        val db = this.writableDatabase
        val update = "UPDATE $TABLE_USERS_SCORES SET $T_USERS_SCORES_COL_6 = \"$comment\" WHERE $T_USERS_SCORES_COL_1 = \"$id\""
        db.execSQL(update)
    }

    fun updateIsLoved(id: Int, loved: Boolean){
        val db = this.writableDatabase
        val isIt : Int = (if(loved) 1 else 0)
        val update = "UPDATE $TABLE_USERS_SCORES SET $T_USERS_SCORES_COL_7 = $isIt WHERE $T_USERS_SCORES_COL_1 = \"$id\""
        db.execSQL(update)
    }
}
