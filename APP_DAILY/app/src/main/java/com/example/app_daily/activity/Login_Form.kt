package com.example.app_daily.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationSet
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_daily.Class.User
import com.example.app_daily.R
import com.example.app_daily.model.Animation
import com.example.app_daily.model.CONVERT
import com.example.app_daily.model.Display
import com.example.app_daily.model.MakeToast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login__form.*

class Login_Form : AppCompatActivity(), FirebaseAuth.AuthStateListener {
    lateinit var mAuth: FirebaseAuth
    lateinit var animationSet: AnimationSet
    lateinit var email: String
    lateinit var password: String
    lateinit var confirmPassword: String
    var currentUser: FirebaseUser? = null
    private val REQUESTS_CODE = 111
    lateinit var mDatabase:FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login__form)
        Display.fullScreen(this)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        imgArrowLeft.setOnClickListener {
            loginToSignUp()
        }
        imgArrowRight.setOnClickListener {
            signupToLogin()
        }
        btSignUp.setOnClickListener {
            email = edt_Email_SignUp.text.toString().trim()
            password = edt_Password_SignUp.text.toString().trim()
            confirmPassword = edt_Confirm_Password_SignUp.text.toString().trim()
            if (checkregister(email, password, confirmPassword)) {
                Animation.buttonLoading(
                    btSignUp,
                    CONVERT.fromImageXmlToBitMap(
                        resources.getDrawable(
                            R.drawable.ic_done_white_48dp,
                            null
                        )
                    ), resources.getDrawable(R.drawable.custom_button_2, null)
                )
                object : CountDownTimer(4000, 500) {
                    override fun onFinish() {
                        createAccount(email, password)
                    }

                    override fun onTick(millisUntilFinished: Long) {
                    }
                }.start()
            }
        }
        btSignIn.setOnClickListener {
            email = edt_Email.text.toString().trim()
            password = edt_Password.text.toString().trim()
            Animation.buttonLoading(
                btSignIn,
                CONVERT.fromImageXmlToBitMap(
                    resources.getDrawable(
                        R.drawable.ic_done_white_48dp,
                        null
                    )
                ), resources.getDrawable(R.drawable.custom_button_2, null)
            )
            object : CountDownTimer(4000, 500) {
                override fun onFinish() {
                    if (!email.isEmpty() || !password.isEmpty()) {
                        if (signInAccount(email, password))
                            signupToLogin()
                    } else {
                        MakeToast.make(applicationContext, "Error!")
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                }
            }.start()

        }
    }

    private fun signupToLogin() {
        Handler().postDelayed({
            layout_login.visibility = View.VISIBLE
            layout_signUp.visibility = View.GONE
        }, 1500)
        animationRegister()
    }

    private fun loginToSignUp() {
        Handler().postDelayed({
            layout_login.visibility = View.GONE
            layout_signUp.visibility = View.VISIBLE
        }, 2000)
        animationLogin()
        edt_Confirm_Password_SignUp.setText("")
        edt_Email_SignUp.setText("")
        edt_Password_SignUp.setText("")
        checkBoxConfirm.isChecked = false
    }

    private fun animationLogin() {
        animationSet = Animation.creatAnimation(false)
        val rotate = Animation.rotate(
            0F, 180F,
            Display.getWidthImageView(imgArrowLeft) / 2.toFloat(),
            Display.getHeightImageView(imgArrowLeft) / 2.toFloat(),
            2000,
            0
        )
        animationSet.addAnimation(rotate)
        imgArrowLeft.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(false)
        val alphaAnimation = Animation.alpha(1F, 0F, 2000, 0)
        animationSet.addAnimation(alphaAnimation)
        tvSignUp.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(false)
        val alphaAnimation_ = Animation.alpha(0F, 1F, 1000, 0)
        animationSet.addAnimation(alphaAnimation_)
        tvSignIn.startAnimation(animationSet)
    }

    private fun animationRegister() {
        animationSet = Animation.creatAnimation(false)
        val rotate = Animation.rotate(
            0F, -180F,
            Display.getWidthImageView(imgArrowRight) / 2.toFloat(),
            Display.getHeightImageView(imgArrowRight) / 2.toFloat(),
            2000,
            0
        )
        animationSet.addAnimation(rotate)
        imgArrowRight.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(false)
        val alphaAnimation = Animation.alpha(1F, 0F, 2000, 0)
        animationSet.addAnimation(alphaAnimation)
        tvSignIn.startAnimation(animationSet)

        animationSet = Animation.creatAnimation(false)
        val alphaAnimation_ = Animation.alpha(0F, 1F, 1000, 0)
        animationSet.addAnimation(alphaAnimation_)
        tvSignUp.startAnimation(animationSet)
    }

    override fun onStart() {
        super.onStart()
        Display.fullScreen(this)
        FirebaseAuth.getInstance().addAuthStateListener(this)
        updateUI(mAuth.currentUser)
    }

    override fun onResume() {
        super.onResume()
        Display.fullScreen(this)
        if(mAuth.currentUser==null)
            MakeToast.make(this,"null")

    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(this)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this@Login_Form, main_app::class.java))
        } else
            Toast.makeText(this, "Please SignIn", Toast.LENGTH_LONG).show()
    }

    private fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            run {
                if (task.isSuccessful) {
                    MakeToast.make(this, "Register is successful~")
                    currentUser = mAuth.currentUser
                    mDatabase.getReference("Users").child(mAuth.currentUser!!.uid).setValue(User(email,"",password,"",""))
                    FirebaseAuth.getInstance().signOut()
                    signupToLogin()
                } else {
                    MakeToast.make(this, "Account is already exist!")

                }
            }

        }

    }

    private fun signInAccount(email: String, password: String): Boolean {
        var check: Boolean = false
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            run {
                if (task.isSuccessful) {
                    check = true
                    currentUser = mAuth.currentUser!!
                    updateUI(currentUser)
                } else {
                    check = false
                    MakeToast.make(this, "Account or Password not correct")
                }
            }
        }
        return check
    }

    private fun checkregister(
        email: String, password: String, confirmPassword: String
    ): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_Email_SignUp.error = "Email is not invalid!"
            edt_Email_SignUp.requestFocus()
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            edt_Password_SignUp.error = "Password is not invalid!"
            edt_Password_SignUp.requestFocus()
            return false
        }
        if (confirmPassword != password) {
            edt_Confirm_Password_SignUp.error = "Confirm error!"
            edt_Confirm_Password_SignUp.requestFocus()
            return false
        }
        if (!checkBoxConfirm.isChecked) {
            checkBoxConfirm.error = "You must agree terms & conditions"
            checkBoxConfirm.requestFocus()
            return false;
        }
        return true
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        if (p0.currentUser == null) {

        } else {
            p0.currentUser?.getIdToken(true)
                ?.addOnCompleteListener { task ->
                    run {
                        if (task.isSuccessful)
                            Log.d("AAA", "Token: " + task.result?.token)
                        else Log.i("AAA", task.exception.toString())
                    }
                }
        }
    }

    public fun handlerLoginWithFaceBook(view: View) {
        val provides = arrayListOf(
            AuthUI.IdpConfig.FacebookBuilder().build()
        )
        val intent =
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provides).build()
        startActivityForResult(intent, REQUESTS_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUESTS_CODE) {
            val idpResponse = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                currentUser = mAuth.currentUser
                FirebaseDatabase.getInstance().getReference("Users").child(currentUser!!.uid)
                    .setValue(User(
                        CONVERT.ignoreNull(currentUser!!.email.toString())
                        ,CONVERT.ignoreNull(currentUser!!.displayName.toString())
                        ,""
                        ,CONVERT.ignoreNull(currentUser!!.phoneNumber.toString())
                        ,CONVERT.ignoreNull(currentUser!!.photoUrl.toString())))
                updateUI(currentUser)
            } else {
                if (idpResponse == null) {
                    MakeToast.make(applicationContext, "User canceled")
                } else {
                    Log.d("A", idpResponse.error.toString())
                }
            }
        }
    }

    public fun resetPassword(view: View) {
        if (!Patterns.EMAIL_ADDRESS.matcher(edt_Email.text.toString().trim()).matches())
            MakeToast.make(this, "You need enter the E-mail invalid to reset password")
        else {
            mAuth.sendPasswordResetEmail(edt_Email.text.toString().trim())
                .addOnCompleteListener(this) { task: Task<Void> ->
                    run {
                            if(task.isSuccessful) MakeToast.make(this,"Password was send to E-mail")
                        else MakeToast.make(this,"E-mail not exists")
                    }
                }
        }
    }
}
