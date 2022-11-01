package com.example.inmart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.android.synthetic.main.activity_sign_up2.eEmail
import kotlinx.android.synthetic.main.activity_sign_up2.epassword
import java.util.*


class SignUp : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        var db= FirebaseDatabase.getInstance()
        var mRef: DatabaseReference =db.getReference("Personal info")
        var node1= mRef

        setContentView(R.layout.activity_sign_up2)
        btnreg.setOnClickListener {
            val name=username.text.toString()
            val email = eEmail.text.toString()
            val pass =epassword.text.toString()
            val dob=userdob.text.toString()
            sellerbox.setOnCheckedChangeListener { _, isChecked ->

                Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
            }

            customerbox.setOnCheckedChangeListener { _, isChecked ->
                Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
            }

            if(email.isNotEmpty() && pass.isNotEmpty()){
                mAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(applicationContext,"Successful" ,Toast.LENGTH_LONG).show()
                        val intentfirstlog=Intent(this,Login::class.java)
                        startActivity(intentfirstlog)
                        node1.child("Name :").setValue(name)
                        node1.child("Email : ").setValue(email)
                        node1.child("Data of Birth :").setValue(dob)
                        if(sellerbox.isChecked){
                            node1.child("Status").setValue("Seller")
                        }
                        else if(customerbox.isChecked){
                            node1.child("Status").setValue("Customer")
                        }

                    }
                    else{
                        Toast.makeText(applicationContext,it.exception.toString() ,Toast.LENGTH_LONG).show()
                    }

                }

            }else{
                Toast.makeText(applicationContext,"Empty" ,Toast.LENGTH_LONG).show()
            }
    }

    /*private fun verifyReg(){
        val userreg=mAuth?.currentUser
        userreg?.sendEmailVerification()?.addOnCompleteListener {
            if(it.isSuccessful){
                val intentfirstlog=Intent(this,Login::class.java)
                startActivity(intentfirstlog)
            }
            else{
                Toast.makeText(applicationContext,it.exception.toString() ,Toast.LENGTH_LONG).show()
            }
        }
    }*/
}
    data class User(val name:String , val email:String ,
                    val pass :String , val dob : String)

    data class Seller(val name:String , val shopname : String)

    data class Customer(val name:String , val email:String ,
                        val pass :String , val dob : String,
                        val Bio : String)
}


