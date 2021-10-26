package com.example.pemesananlondry.pemesan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.pemesananlondry.MainActivity
import com.example.pemesananlondry.R
import com.example.pemesananlondry.utill.App
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profil_pemesan.*


//Dimasukkan
class ProfilPemesan : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
       // val app = App()

        setContentView(R.layout.activity_profil_pemesan)
        getUserProfil()
        btn_keluarpemesan.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)
        }
//        app.fullScreen()
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
                    //Log.d("apaini", user?.nama!!)
                    // tvalamat_profil_pemilik_bawah.text = user!!.nama
                   // tv_alamatpemesanprofil.text = user!!.alamat
                    //tv_emailprofilpemesan.text= user!!.email
                    tv_emailprofilpemesan1.text = user!!.email
                    tv_namaPemesan.text = user!!.nama
                    tv_alamatpemesanprofil.setText(user!!.alamat)
                    tv_emailprofilpemesan.setText(user!!.email)
                }
            })
    }
}