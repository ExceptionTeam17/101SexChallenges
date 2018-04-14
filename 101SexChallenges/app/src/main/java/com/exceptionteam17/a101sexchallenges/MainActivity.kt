package com.exceptionteam17.a101sexchallenges

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
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
import android.transition.Transition
import android.transition.Explode
import android.transition.TransitionManager
import android.transition.TransitionListenerAdapter




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
            startActivityForResult(Intent(this, AgeCheck::class.java), ageCode)
        }
        if(ShPrefs.isFirstRun(this)) { // first run
//        if(true){
            db!!.addOnFirstRun()
            ShPrefs.setIsFirstRun(this, false)
        }

        main_text_progress.text = getString(R.string.progress_main, ShPrefs.getProgress(this))
        main_text_progress.setShadowLayer(30F, 0F, 0F, Color.parseColor("#FFF47070"))
        data = db!!.getData()
        data!!.forEach { Log.e("Bla", it.text)} // TODO test only

        Data.load(data!!)

        adapter = MyGridAdapter(this)
        main_grid.adapter = adapter

        main_grid.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    var inte = Intent(this, ChallengeActivity::class.java)
                    inte.putExtra("id", position)

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        val viewRect = Rect()
                        v.getGlobalVisibleRect(viewRect)

                        var explode: Transition = Explode()
                        explode.epicenterCallback = object : Transition.EpicenterCallback() {
                            override fun onGetEpicenter(transition: Transition): Rect {
                                return viewRect
                            }
                        }

//                        val sharedElementEnterTransition = window.sharedElementEnterTransition

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            sharedElementEnterTransition.addListener(object : TransitionListenerAdapter() {
//                                override fun onTransitionEnd(transition: Transition) {
//                                    super.onTransitionEnd(transition)
//                                }
//                            })
//                        } else {
                            explode.addListener(object : Transition.TransitionListener {
                                override fun onTransitionStart(transition: Transition) {}

                                override fun onTransitionEnd(transition: Transition) {
                                    startActivityForResult(inte, challengeCode)
                                }

                                override fun onTransitionCancel(transition: Transition) {}

                                override fun onTransitionPause(transition: Transition) {}

                                override fun onTransitionResume(transition: Transition) {}
                            })
//                        }
                        explode.duration = 300
                        TransitionManager.beginDelayedTransition(main_grid, explode)
                        main_grid.adapter = null

                    } else {

                        startActivityForResult(inte, challengeCode)
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == challengeCode && resultCode == Activity.RESULT_OK){
            main_text_progress.text = getString(R.string.progress_main, ShPrefs.getProgress(this))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                main_grid.adapter = adapter
            }
            adapter!!.notifyDataSetChanged()

        }else if (requestCode == challengeCode && resultCode == Activity.RESULT_CANCELED){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                main_grid.adapter = adapter
            }
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
            itemView!!.grid_number.text = item.id.toString()

            when(item.state){
                Challenge.NEW -> {
                    itemView.grid_img.setImageResource(R.drawable.new_state)
                    itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)
                }
                Challenge.DONE -> {
                    if(item.isLoved){
                        itemView.grid_img.setImageResource(R.drawable.love)
                        itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)
                    } else {
                        itemView.grid_img.setImageResource(R.drawable.done_state)
                        itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)

                    }
                }
                Challenge.OPPEND -> {
                    if(item.isLoved){
                        itemView.grid_img.setImageResource(R.drawable.open_love)
                        itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)
                    } else {
                        itemView.grid_img.setImageResource(R.drawable.open)
                        itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)
                    }
                }
                Challenge.NOTNOW -> {
                    if(item.isLoved){
                        itemView.grid_img.setImageResource(R.drawable.notnowlove)

                    } else {
                        itemView.grid_img.setImageResource(R.drawable.notnow)
                    }
//                    itemView.grid_number.setTextColor(Color.BLACK)
                    itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.RED) //todo ADD TO ALL
                }
                Challenge.NEVER -> {
                    if(item.isLoved){
                        itemView.grid_img.setImageResource(R.drawable.never_love_state)
                        itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)
                    } else {
                        itemView.grid_img.setImageResource(R.drawable.never)
                        itemView.grid_number.setShadowLayer(35F, 0F, 0F, Color.BLUE)

                    }
                }
            }
            return itemView
        }
    }
}
