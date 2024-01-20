package com.example.recycleview2

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random
import  kotlin.collections.mutableListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profileItemList = mutableListOf<ProfileItem>()

        for(i in 0..99){
            val randomNumber = Random.nextInt(0, 100)
            if(randomNumber % 2 == 0 ){
                profileItemList.add(
                    ProfileItem(randomNumber.toString(),"desc $i",
                        verify = false,
                        isSelected = false
                    )
                )
            }else{
                profileItemList.add(
                    ProfileItem(randomNumber.toString(),"desc $i",
                        verify = true,
                        isSelected = false
                    )
                )
            }
        }


        val recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = RecyclerView.VERTICAL
            }
            adapter = ProfileRecycleAdapter(this@MainActivity,profileItemList)
        }

        setContentView(recyclerView)
    }
}


class ProfileRecycleAdapter(val context: Context,var data : MutableList<ProfileItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemViewType(position: Int): Int {
        return when (data[position].verify) {
            true -> 1
            false-> 2
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == 1 ) VerifyProfileHolder(VerifyProfileView(context))  else  ProfileViewHolder(ProfileView(context))


    }

    override fun getItemCount(): Int {
      return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(data[position].verify){
            true-> {val tHolder = holder as VerifyProfileHolder
                tHolder.nameTextView.text = data[position].name
                tHolder.detailsTextView.text = data[position].details
            }
            false ->{
                val tHolder = holder as ProfileViewHolder
                tHolder.nameTextView.text = data[position].name
                tHolder.detailsTextView.text = data[position].details
            }
        }

    }

}

class VerifyProfileHolder(itemView : VerifyProfileView) : RecyclerView.ViewHolder(itemView){
    val nameTextView = itemView.nameTextView
    val detailsTextView = itemView.detailsTextView
}

class ProfileViewHolder(itemView : ProfileView) :  RecyclerView.ViewHolder(itemView){
    val nameTextView = itemView.nameTextView
    val detailsTextView = itemView.detailsTextView
}





data class ProfileItem(val name : String,val details: String ,var verify: Boolean , var isSelected : Boolean)


open class ProfileView(context : Context) : LinearLayout(context){
    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    val nameTextView : TextView = TextView(context).apply {
        this@ProfileView.addView(this)
    }
    val detailsTextView : TextView = TextView(context).apply {
        this@ProfileView.addView(this)
    }
}

class VerifyProfileView(context : Context) : ProfileView(context){
    val changeThem : Button = Button(context).apply {
        text = "change theam"
        this@VerifyProfileView.addView(this)
        setOnClickListener {
            this@VerifyProfileView.setBackgroundColor(Color.CYAN)
        }
    }

}


