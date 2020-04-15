package com.example.app_daily.model

import android.app.Activity
import android.content.Context
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat

object Display{

    public fun getWidthTextView(view:View):Int
    {
        val viewTextView = view as TextView
        viewTextView.measure(0,0)
        return viewTextView.measuredWidth
    }
    public fun getHeightTextView(view:View):Int
    {
        val viewTextView = view as TextView
        viewTextView.measure(0,0)
        return viewTextView.measuredHeight
    }
    public fun getWidthImageView(view:ImageView):Int
    {
        return view.drawable.intrinsicWidth
    }
    public fun getHeightImageView(view:ImageView):Int
    {
        return view.drawable.intrinsicWidth
    }
    public fun fullScreen(context: Activity) {
        context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

}