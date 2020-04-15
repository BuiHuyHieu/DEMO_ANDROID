package com.example.app_daily.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationSet
import androidx.appcompat.app.AppCompatActivity
import com.example.app_daily.R
import com.example.app_daily.model.Animation
import com.example.app_daily.model.Display
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var animationSet: AnimationSet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Display.fullScreen(this)
        initAnimation();
        Handler().postDelayed({
            startActivity(Intent(this@MainActivity,Login_Form::class.java))
            overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit)
            finish()
        },8500)
    }

    override fun onStart() {
        super.onStart()
        Display.fullScreen(this)
    }

    override fun onResume() {
        super.onResume()
        Display.fullScreen(this)
    }


    private fun initAnimation() {
        animationSet= Animation.creatAnimation(true)
        animationSet.addAnimation(Animation.Translate(
            this
            , imgHeadLine
            , imgHeadLine.x
            , imgHeadLine.x
            , -Display.getHeightImageView(imgHeadLine).toFloat()
            , 0F
            , 2000
            ,0
        ));
        animationSet.setInterpolator(this,R.anim.custome_bound_anim)
        imgHeadLine.startAnimation(animationSet)

       animationSet = Animation.creatAnimation(false)
       animationSet.addAnimation(Animation.Translate(
            this
            , tvBottom
            , tvBottom.x
            , tvBottom.x
            , 5 * Display.getHeightTextView(tvBottom).toFloat()
            , 0F
            , 2000,1000
       ));
           tvBottom.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(true)
        animationSet.addAnimation(Animation.Translate(

            this
            , tvDaily
            , 2.5F * Display.getWidthTextView(tvDaily).toFloat()
            , 0F
            , 0F
            , 0F
            , 2000,2500
        ))
        val animationAlpha = Animation.alpha(1F,0.3F,2100,5500)
        animationAlpha.repeatMode = android.view.animation.Animation.REVERSE
        animationSet.addAnimation(animationAlpha)
        animationSet.setInterpolator(this,R.anim.speed_fast_to)
        tvDaily.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(true)
        animationSet.addAnimation(Animation.Translate(
            this
            , tvDaily
            , -2.8F * Display.getWidthTextView(tvDaily).toFloat()
            , 0F
            , 0F
            , 0F
            , 5000,2500
        ))
        animationSet.setInterpolator(this,R.anim.speed_fast_to)
        imgLoading.startAnimation(animationSet)

        animationSet= Animation.creatAnimation(true)
        animationSet.addAnimation(Animation.Translate(
            this
            , imgHeadLine
            , imgHeadLine.x
            , imgHeadLine.x
            , -Display.getHeightImageView(imgIpad).toFloat()
            , 0F
            , 3000
            ,1000
        ));
        animationSet.setInterpolator(this,R.anim.overshoot)
        imgIpad.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(true)
        animationSet.addAnimation(Animation.alpha(0.0F,1F,2000,4000))
        imgWelcome.startAnimation(animationSet)

    }


}
