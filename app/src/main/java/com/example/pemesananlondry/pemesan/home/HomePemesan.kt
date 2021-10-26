package com.example.pemesananlondry.pemesan.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesananlondry.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


//Dimasukkan
class HomePemesan : AppCompatActivity() {
    private var list : MutableList<HomePemesanModel> = ArrayList()
    private lateinit var rvData: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pemesan)
        fungsiRecyleView()
    }

    //Dimasukkan Dipisah
    private fun fungsiRecyleView() {
    //deklarasi RV
        val listadapter = HomePemesanAdapter(this,list)
        rvData.adapter = listadapter
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //deklarasi untuk Database
        var myRef = FirebaseDatabase.getInstance().getReference("Pengelola")
        //isi data di RV
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val x = snap.getValue(HomePemesanModel::class.java)
                    //Log.e("testsoal", Gson().toJson(x))
                    list.add(x!!)
                    listadapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}