package com.example.pemesananlondry.pengelola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pengelola.Pesanan.PesananPemilik
import kotlinx.android.synthetic.main.activity_home_pengelola.*

//Dimasukkan
class HomePengelola : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pengelola)
        btn_pesan_pengelola.setOnClickListener {
            val intent = Intent(this, PesananPemilik::class.java)
            startActivity(intent)
        }

        btn_profil_pengelola.setOnClickListener {
            val intent = Intent(this, ProfilPengelola::class.java)
            startActivity(intent)
        }
    }
}