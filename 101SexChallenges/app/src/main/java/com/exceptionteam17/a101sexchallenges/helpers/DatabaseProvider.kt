package com.exceptionteam17.a101sexchallenges.helpers

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher



class DatabaseProvider: ContentProvider() {
    companion object {
        val CONTENT_AUTHORITY = "com.exceptionteam17.a101sexchallenges"
        val BASE_CONTENT_URI = Uri.parse("content://com.exceptionteam17.a101sexchallenges")
        val TABLE_NAME = "challengesSex101.db"

    }

    private val sUriMatcher = buildUriMatcher()
    private var mOpenHelper: DatabaseHelper? = null

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun query(p0: Uri, p1: Array<String>?, p2: String?, p3: Array<String>?, p4: String?): Cursor? {
        var cursor: Cursor? = null

        when (sUriMatcher.match(p0)){
            889 -> {
                cursor = mOpenHelper?.exportData()
                try {
                    cursor!!.setNotificationUri(context!!.contentResolver, p0)
                }catch (ignored: Exception){}
            }
        }
        return cursor
    }

    override fun onCreate(): Boolean {
        if (context == null)
            return false
        mOpenHelper = DatabaseHelper.getInstance(context!!)
        return mOpenHelper != null
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int {
        return 0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    private fun buildUriMatcher(): UriMatcher {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        val authority = CONTENT_AUTHORITY
        matcher.addURI(authority, "challengesSex101.db", 889)

        return matcher
    }
}