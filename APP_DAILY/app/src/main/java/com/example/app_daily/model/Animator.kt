package com.example.app_daily.model

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.view.View

object Animator{

    public fun creatAnimator():AnimatorSet
    {
        return AnimatorSet()
    }
    public fun alpha(view: View, from:Float, to:Float , delay:Long):ObjectAnimator{
        val objectAnimator = ObjectAnimator.ofFloat(view,"alpha",from,to)
        objectAnimator.duration = 500
        objectAnimator.startDelay= delay.toLong()
        return objectAnimator
    }

}