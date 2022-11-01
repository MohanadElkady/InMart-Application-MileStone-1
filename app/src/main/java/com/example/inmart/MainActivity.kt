package com.example.inmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userlocation.setOnClickListener {
            val intentmap=Intent(this,MapActivity::class.java)
            startActivity(intentmap)
        }
        extradata.setOnClickListener {
            val intentmap=Intent(this,ExtraCustomerdata::class.java)
            startActivity(intentmap)
        }
        mAuth= FirebaseAuth.getInstance()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item?.itemId
        if(id==R.id.itemLogout){
            FirebaseAuth.getInstance().signOut()
            val logafterout=Intent(this ,Login::class.java)
            startActivity(logafterout)
        }
        return true
    }
}