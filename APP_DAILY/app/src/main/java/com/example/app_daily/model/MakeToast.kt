package com.example.app_daily.model

import android.content.Context
import android.widget.Toast

object MakeToast{

    public fun make(contect: Context, msg:String)
    {
        Toast.makeText(contect,msg,Toast.LENGTH_SHORT).show()
    }

}