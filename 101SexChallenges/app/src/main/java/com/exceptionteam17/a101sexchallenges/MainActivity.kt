package com.exceptionteam17.a101sexchallenges

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.exceptionteam17.a101sexchallenges.helpers.Data
import com.exceptionteam17.a101sexchallenges.helpers.DatabaseHelper
import com.exceptionteam17.a101sexchallenges.helpers.ShPrefs
import com.exceptionteam17.a101sexchallenges.model.Challenge
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_elem.view.*

class MainActivity : AppCompatActivity() {

    private val ageCode = 666
    private val challengeCode = 668

    private var db: DatabaseHelper? = null
    private var data: ArrayList<Challenge>? = null
    private var adapter: MyGridAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        db = DatabaseHelper.getInstance(this)

        if(ShPrefs.isAgeChecked(this)) { // first run
//        if(true){
            startActivityForResult(Intent(this, AgeCheck::class.java), ageCode)
        }
        if(ShPrefs.isFirstRun(this)) { // first run
//        if(true){
            db!!.addOnFirstRun()
            ShPrefs.setIsFirstRun(this, false)
        }

        main_text_progress.text = getString(R.string.progress_main, ShPrefs.getProgress(this))
        data = db!!.getData()
        data!!.forEach { Log.e("Bla", it.text)} // TODO test only

        Data.load(data!!)

        adapter = MyGridAdapter(this)
        main_grid.adapter = adapter

        main_grid.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    var inte = Intent(this, ChallengeActivity::class.java)
                    inte.putExtra("id", position)
                    startActivityForResult(inte, challengeCode)
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == challengeCode && resultCode == Activity.RESULT_OK){
            adapter!!.notifyDataSetChanged()

        }else if (requestCode == challengeCode && resultCode == Activity.RESULT_CANCELED){
            adapter!!.notifyDataSetChanged()

        } else if(requestCode == ageCode && resultCode == Activity.RESULT_CANCELED){
            finish()

        } else if (requestCode == ageCode && resultCode == Activity.RESULT_OK){
            ShPrefs.ageComfirmed(this, false)
        }
    }


    class MyGridAdapter(context: Context) : BaseAdapter() {

        class ViewHolder (var image : ImageView, var text : TextView)

        var context: Context? = context

        override fun getCount(): Int {
            return Data.list!!.size
        }

        override fun getItem(position: Int): Any {
            return Data.list!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val item = Data.list!![position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var itemView = convertView ?: inflator.inflate(R.layout.grid_elem, null) //WOW !!!
            itemView!!.grid_number.text = (position + 1).toString()

            when(item.state){
                1 -> {
                    //itemView.grid_img.setImageBitmap()
                    itemView.grid_number.setTextColor(Color.BLACK)
                }
                2 -> {
                    //itemView.grid_img.setImageBitmap()
                    itemView.grid_number.setTextColor(Color.BLACK)
                }
                3 -> {
                    //itemView.grid_img.setImageBitmap()
                    itemView.grid_number.setTextColor(Color.BLACK)
                }
                4 -> {
                    //itemView.grid_img.setImageBitmap()
                    itemView.grid_number.setTextColor(Color.BLACK)
                }
                5 -> {
                    //itemView.grid_img.setImageBitmap()
                    itemView.grid_number.setTextColor(Color.BLACK)
                }
            }
            return itemView
        }
    }
}
