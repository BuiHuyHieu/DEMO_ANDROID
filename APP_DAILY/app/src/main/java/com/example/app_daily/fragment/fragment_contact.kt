package com.example.app_daily.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.app_daily.R
import com.example.app_daily.model.MakeToast
import kotlinx.android.synthetic.main.fragment_contact.view.*

/**
 * A simple [Fragment] subclass.
 */
class fragment_contact : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(android.Manifest.permission.CAMERA),
            1
        )


        val isFlaslight =
            activity!!.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (loadingSharedPref()) {
            view.button_Flash.setImageResource(R.mipmap.ic_light_on_round)
            openFlash()
        } else {
            view.button_Flash.setImageResource(R.mipmap.ic_light_off_round)
            closeFlash()
        }
        view.button_Flash.setOnClickListener {
            if (!isFlaslight)
                MakeToast.make(activity!!,"Device not available!")
                if (loadingSharedPref()) {
                    view.button_Flash.setImageResource(R.mipmap.ic_light_off_round)
                    openFlash()
                } else {
                    view.button_Flash.setImageResource(R.mipmap.ic_light_on_round)
                    closeFlash()
                }
                val temp = !loadingSharedPref()
                updateSharedPref(temp)
        }
        view.button_callPhone.setOnClickListener{
            ActivityCompat.requestPermissions(activity!!,arrayOf(Manifest.permission.CALL_PHONE),2)
            val intent = Intent(Intent.ACTION_DIAL)
            startActivity(Intent.createChooser(intent,"Please choose app:"))
        }
        view.button_sendSmS.setOnClickListener {
            ActivityCompat.requestPermissions(activity!!,arrayOf(Manifest.permission.SEND_SMS),3)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent,"Please choose app:"))
        }
        return view
    }

    private fun updateSharedPref(ischecked: Boolean) {
        val sharedPreferences = activity!!.getSharedPreferences("contact", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putBoolean("on_off_flashlight", ischecked)
        edit.apply()
        edit.commit()
    }

    private fun loadingSharedPref(): Boolean {
        val sharedPreferences = activity!!.getSharedPreferences("contact", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("on_off_flashlight", false)
    }
    private fun openFlash()
    {
        var cameraManager:CameraManager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
 //       cameraManager.setTorchMode(cameraId,true)
    }
    private fun closeFlash()
    {
        var cameraManager:CameraManager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
//        cameraManager.setTorchMode(cameraId,false)
    }
}
