package com.example.app_daily.fragment

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.app_daily.Class.User
import com.example.app_daily.R
import com.example.app_daily.model.CONVERT
import com.example.app_daily.model.MakeToast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_login__form.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class fragment_profile : Fragment(),View.OnClickListener {
    lateinit var mAuth:FirebaseAuth
    val mDatabase = FirebaseDatabase.getInstance()
    var uri_Photo:String?= ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        mAuth = FirebaseAuth.getInstance()
        view.button_SignOut.setOnClickListener {
            mAuth.signOut()
            AuthUI.getInstance().signOut(activity!!)
            activity!!.finish()
        }
        setProfile(view)
        if(mAuth.currentUser!!.photoUrl.toString().contains("facebook"))
        {
            view.edt_Phone_Profile.focusable = View.NOT_FOCUSABLE
            view.edt_fullName_Profile.focusable = View.NOT_FOCUSABLE
        }
        else setImage(view)
        view.button_Update.setOnClickListener(this)
        return view
    }
    override fun onClick(v: View?) {
        when(v)
        {
            button_Update->{
                MakeToast.make(activity!!,"Click")
                val email = edt_Email_Profile.text.toString().trim()
                val name =  edt_fullName_Profile.text.toString().trim()
                val phone = edt_Phone_Profile.text.toString().trim()
                val password = edt_Password_Profile.text.toString().trim()
                val user = User(email,name, password, phone,uri_Photo).toMap()
                mDatabase.reference.child("Users").child(mAuth.currentUser!!.uid)
                    .updateChildren(user)
            }
        }
    }
    private fun setProfile(view:View){
        mDatabase.reference.child("Users").child(mAuth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(p0: DataSnapshot) {
                     val user = p0.getValue<User>()
                    view.edt_fullName_Profile.setText(user?.name.toString())
                    view.edt_Email_Profile.setText(user?.email.toString())
                    view.edt_Phone_Profile.setText(user?.phone.toString())
                    view.edt_Password_Profile.setText(user?.password.toString())
                    if(!user?.photo_Uri?.isEmpty()!!)
                    {
                        if(user.photo_Uri!!.contains("facebook"))
                        {
                            Glide.with(view)
                                .load(user.photo_Uri!!)
                                .centerCrop()
                                .into(view.img_Avatar)
                        }
                        else
                        view.img_Avatar.setImageBitmap(CONVERT.fromStringToBitMap(user?.photo_Uri!!))
                    }
                    view.avatar_Name.setText(view.edt_fullName_Profile.text)
                    view.avatar_Email.setText(view.edt_Email_Profile.text)
                }
            })
    }
    private fun setImage(view:View)
    {
        view.img_Avatar.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode == RESULT_OK)
        {
            val uri = data?.data
            val inputStream = context!!.contentResolver.openInputStream(uri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            uri_Photo = CONVERT.fromBitmapToString(bitmap)
            this.img_Avatar.setImageBitmap(bitmap)
        }
    }

}
