package com.example.app_daily.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.sax.EndElementListener
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener

object Animation {

    public fun creatAnimation(boolean: Boolean): AnimationSet {
        return AnimationSet(boolean)
    }

    public fun Translate(
        context: Context,
        view: View,
        from_x: Float,
        to_x: Float,
        from_y: Float,
        to_y: Float,
        duration: Long,
        startOffset: Long
    ): TranslateAnimation {
        val translateAnimation = TranslateAnimation(from_x, to_x, from_y, to_y)
        translateAnimation.duration = duration
        translateAnimation.startOffset = startOffset
        return translateAnimation
    }

    public fun alpha(
        from_alpha: Float,
        to_alpha: Float,
        duration: Long,
        startOffset: Long
    ): AlphaAnimation {

        val alphaAnimation = AlphaAnimation(from_alpha, to_alpha)
        alphaAnimation.duration = duration
        alphaAnimation.startOffset = startOffset
        return alphaAnimation
    }

    public fun rotate(
        from_deg: Float,
        end_deg: Float,
        x_delta: Float,
        y_delta: Float,
        duration: Long,
        startOffset: Long
    ): RotateAnimation {
        val rotateAnimation = RotateAnimation(from_deg, end_deg, x_delta, y_delta)
        rotateAnimation.duration = duration
        rotateAnimation.startOffset = startOffset
        return rotateAnimation
    }
    public fun buttonLoading(button:CircularProgressButton,bitmap:Bitmap,drawable:Drawable?)
    {
        val countDownTimer = object:CountDownTimer(2000,500) {
            override fun onFinish() {
                object:CountDownTimer(2000,500){
                    override fun onFinish() {
                        button.revertAnimation { button.background = drawable }
                    }
                    override fun onTick(millisUntilFinished: Long) {
                       button.doneLoadingAnimation(Color.GREEN,bitmap)
                    }
                }.start()
            }
            override fun onTick(millisUntilFinished: Long) {
                button.startAnimation()
            }

        }.start()
    }
}
