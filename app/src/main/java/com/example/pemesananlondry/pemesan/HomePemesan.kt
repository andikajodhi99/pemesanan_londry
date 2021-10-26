package com.example.pemesananlondry.pemesan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pemesananlondry.R
import kotlinx.android.synthetic.main.activity_home_pemesan.*


//Dimasukkan
class HomePemesan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pemesan)

        btn_profilpesan.setOnClickListener {
            val intent= Intent(this,ProfilPemesan::class.java)
            startActivity(intent)
        }
        btn_pesan_pemesan.setOnClickListener {
            val intent= Intent(this,PesananPemesan::class.java)
            startActivity(intent)
        }
    }
}
