package com.example.pemesananlondry.pengelola.PesananDiterima

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.home.HomePemesanAdapter
import com.example.pemesananlondry.pemesan.home.HomePemesanModel
import com.example.pemesananlondry.pengelola.Pesanan.PesananPemilik
import com.example.pemesananlondry.pengelola.ProfilPengelola
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_pengelola.*
import kotlinx.android.synthetic.main.activity_pesananmasuk.*


//Dimasukkan
class PesananDiterimaPemilik : AppCompatActivity() {

    private var list : MutableList<PesananDiterimaModel> = ArrayList()
    private lateinit var rvData: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pengelola)
        rvData = findViewById(R.id.rv_dataditerimahome)
        rvData.setHasFixedSize(true)
        fungsiRecyleView()
        btn_pesan_pengelola.setOnClickListener {
            val intent = Intent(this, PesananPemilik::class.java)
            startActivity(intent)
        }

        btn_profil_pengelola.setOnClickListener {
            val intent = Intent(this, ProfilPengelola::class.java)
            startActivity(intent)
        }
    }

    private fun fungsiRecyleView() {
        val listadapter = PesananDiterimaAdapter(this,list)
        rvData.adapter = listadapter
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //deklarasi untuk Database
        var myRef = FirebaseDatabase.getInstance().getReference("DataPesanDiambil")
        //isi data di RV
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val x = snap.getValue(PesananDiterimaModel::class.java)
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