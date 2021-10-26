package com.example.pemesananlondry.pengelola.Pesanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.home.HomePemesanAdapter
import com.example.pemesananlondry.pemesan.home.HomePemesanModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_pesananmasuk.*


//Dimasukkan
class PesananPemilik : AppCompatActivity() {

    private var list : MutableList<PesananModel> = ArrayList()
    private lateinit var rvData: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesananmasuk)
        rvData = findViewById(R.id.rv_pesananmasuk)
        rvData.setHasFixedSize(true)
        fungsiRecyleView()
    }

    //Dimasukkan
    private fun fungsiRecyleView() {
        val listadapter = PesananAdapter(this,list)
        rvData.adapter = listadapter
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //deklarasi untuk Database
        var myRef = FirebaseDatabase.getInstance().getReference("DataPesan")
        //isi data di RV
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val x = snap.getValue(PesananModel::class.java)
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