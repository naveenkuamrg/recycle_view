package com.example.recycleview2

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlin.random.Random
import  kotlin.collections.mutableListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profileItemList = mutableListOf<News>()

        for(i in 0..99){
            val randomNumber = Random.nextInt(0, 100)
            if(randomNumber % 2 == 0 ){
                profileItemList.add(
                    News(" $i Odisha government seeks urgent deployment of kumki elephants from Tamil Nadu"
                        ,"The Odisha government has sought assistance of Tamil Nadu for making kumki elephants and mahouts available in the wake of worsening human-elephant conflict in the eastern State",
                        hotNews = false,
                        isSelected = false
                    )
                )
            }else{
                profileItemList.add(
                    News("$i Odisha government seeks urgent deployment of kumki elephants from Tamil Nadu","" +
                            "The Odisha government has sought assistance of Tamil Nadu for making kumki elephants and mahouts available in the wake of worsening human-elephant conflict in the eastern State"
                            ,hotNews = true,
                        isSelected = false
                    )
                )
            }
        }


        val recyclerView = RecyclerView(this).apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL).apply {

            }
            adapter = ProfileRecycleAdapter(this@MainActivity,profileItemList)
        }

        setContentView(recyclerView)
    }
}


class ProfileRecycleAdapter(val context: Context,var data : MutableList<News>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemViewType(position: Int): Int {
        return when (data[position].hotNews) {
            true -> 1
            false-> 2
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("Check","onCreateViewHolder")
        return if(viewType == 1 ) LargeNewsHolder(LargeNewsView(context))  else  SmallNewsHolder(SmallNewsView(context))


    }



    override fun getItemCount(): Int {
      return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("Check","onBindViewHolder")
          when(data[position].hotNews){
            true-> {val tHolder = holder as LargeNewsHolder
                tHolder.nameTextView.text = data[position].headNews
                tHolder.detailsTextView.text = data[position].details
                tHolder.button.setOnClickListener {
                    data[position].isSelected = !data[position].isSelected
                    notifyDataSetChanged()
                }


            }
            false ->{
                val tHolder = holder as SmallNewsHolder
                tHolder.nameTextView.text = data[position].headNews
            }
        }
        if(data[position].isSelected ){
            holder.itemView.setBackgroundColor(Color.CYAN)
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }


    }

}

class LargeNewsHolder(itemView : LargeNewsView) : RecyclerView.ViewHolder(itemView){
    val nameTextView = itemView.nameTextView
    val detailsTextView = itemView.detailsTextView
    val button = itemView.changeThem
}

class SmallNewsHolder(itemView : SmallNewsView) :  RecyclerView.ViewHolder(itemView){
    val nameTextView = itemView.nameTextView
}





data class News(val headNews : String, val details: String, var hotNews: Boolean, var isSelected : Boolean)


open class SmallNewsView(context : Context) : LinearLayout(context){
    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    val nameTextView : TextView = TextView(context).apply {
        maxWidth = 300
        this@SmallNewsView.addView(this)
    }

}

class LargeNewsView(context : Context) : SmallNewsView(context){

    val detailsTextView : TextView = TextView(context).apply {
        maxWidth = 300
        maxLines = 3
        this@LargeNewsView.addView(this)
    }
    val changeThem : Button = Button(context).apply {
        text = "change theam"
        maxWidth = 300
        this@LargeNewsView.addView(this)
    }

}

class CustomLayoutManager(context : Context) : LinearLayoutManager(context){


    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        Log.i("Check","onAttachedToWindow")
    }

    override fun addView(child: View?) {
        super.addView(child)
        Log.i("Check","addView")
    }

    override fun attachView(child: View) {
        super.attachView(child)
        Log.i("Checks","attachView")
    }


    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        super.onAdapterChanged(oldAdapter, newAdapter)
        Log.i("Check","onAdapterChanged")
    }

    override fun addDisappearingView(child: View?) {
        super.addDisappearingView(child)
        Log.i("Check","addDisappearingView")
    }

}



