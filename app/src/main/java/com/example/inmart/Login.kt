package com.example.inmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        btnsignup.setOnClickListener {
            // to intent to sign up page
            val intentsignup = Intent(this, SignUp::class.java)
            startActivity(intentsignup)
        }
        btnlog.setOnClickListener {
            login()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth?.currentUser != null ) {
            var intentLog = Intent(this, MainActivity::class.java)
            startActivity(intentLog)
        }
    }

    private fun login() {
        val elogmail = logmail.text.toString()
        val elogpass = logpass.text.toString()

        if (elogmail.isNotEmpty() && elogpass.isNotEmpty())
            mAuth?.signInWithEmailAndPassword(elogmail, elogpass)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val intentmain = Intent(this, MainActivity::class.java)
                    startActivity(intentmain)
                } else {
                    Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        else {
            Toast.makeText(applicationContext, "Empty", Toast.LENGTH_LONG).show()
        }
        /*private fun verifyEmail(){
        val user=mAuth?.currentUser
        if(user!!.isEmailVerified){
            val intentmain=Intent(this,MainActivity::class.java)
            startActivity(intentmain)
        }else{
            Toast.makeText(applicationContext,"Please verify your account !" , Toast.LENGTH_LONG).show()
        }
    }*/
    }
}