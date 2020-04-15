package com.example.app_daily.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.util.*

object CONVERT {

    public fun fromImageXmlToBitMap(drawable: Drawable): Bitmap{
        var bitmap:Bitmap
        bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,drawable.intrinsicHeight,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,canvas.width,canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
    public fun fromBitmapToString(bitmap:Bitmap):String?
    {
        val byteArrayOutPutStream = object:ByteArrayOutputStream(){}
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutPutStream)
        val byte = byteArrayOutPutStream.toByteArray()
        return Base64.encodeToString(byte,Base64.DEFAULT)
    }
    public fun fromStringToBitMap(content:String):Bitmap?{
        val byteArray = Base64.decode(content,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

    public fun ignoreNull(ob:Any?):String{
         if(ob.toString() == "null")
             return ""
        else return ob.toString()
    }
}