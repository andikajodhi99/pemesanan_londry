package com.example.pemesananlondry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity2 : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Halo Test TJ")

        myRef.setValue("Halo Test Test Dari TJ!")

    }
}