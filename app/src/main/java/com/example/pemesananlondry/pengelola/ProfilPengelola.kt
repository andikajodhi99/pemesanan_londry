package com.example.pemesananlondry.pengelola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pemesananlondry.MainActivity
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.Pemesan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profil_pemesan.*
import kotlinx.android.synthetic.main.activity_profil_pengelola.*


//Dimasukkan
class ProfilPengelola : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_pengelola)
        getUserProfil()
        auth = FirebaseAuth.getInstance()
        btn_keluarpengelola.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)
        }

    }

    private fun getUserProfil() {
        val dataRef = FirebaseDatabase.getInstance().getReference("User")
        var user: Pemesan? = null
        val currentuser = FirebaseAuth.getInstance().currentUser?.email.toString()

        dataRef.orderByChild("email").equalTo(currentuser)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        //Log.d("apaini", user?.nama!!)
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        for (snap in p0.children) {
                            user = snap.getValue(Pemesan::class.java)
                        }
                        tv_emailpengelola1.text = user!!.email
                        tv_namapengelola.text = user!!.nama
                        tv_alamatpengelola.setText(user!!.alamat)
                        tv_emailpengelola.setText(user!!.email)
                    }
                })
    }
}