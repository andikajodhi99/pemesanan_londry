package com.example.pemesananlondry.pemesan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.pemesananlondry.MainActivity
import com.example.pemesananlondry.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_pesanan_pemesan.*
import kotlinx.android.synthetic.main.activity_registrasi.*


//Dimasukkan
class PesananPemesan : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_pemesan)
        ref = FirebaseDatabase.getInstance().getReference("DataPesan")
        btn_pesan_pemesanpesan.setOnClickListener {
            Cekdatapesan()
        }

        radiogrup.setOnCheckedChangeListener({
            group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
                    if (radio.text=="Ekonomi [6 hari]"){
                        total_harga.setText("5000/kg")
                        paket.setText("${radio.text}")
                    }
                    if (radio.text=="Reguler[3-5 hari]"){
                        total_harga.setText("8000/kg")
                        paket.setText("${radio.text}")
                    }
                    if (radio.text=="Cepat [2 hari]"){
                        total_harga.setText("10000/kg")
                        paket.setText("${radio.text}")
                    }
                }
        )
    }

    //Dimasukkan Dipisah
    private fun Cekdatapesan() {

        if (et_nama_pemesan.text.toString().isEmpty()) {
            et_nama_pemesan.error = "Masukkan Nama "
            et_nama_pemesan.requestFocus()
            return
        }
        if (et_alamat_pemesan.text.toString().isEmpty()) {
            et_alamat_pemesan.error = "Masukkan Alamat"
            et_alamat_pemesan.requestFocus()
            return
        }
        if(et_notel_pemesan.text.toString().isEmpty()){
            et_notel_pemesan.error = "Masukkan Nomer Telfon"
            et_notel_pemesan.requestFocus()
            return
        }
        if (et_notel_pemesan.text.toString().length < 9 ){
            et_notel_pemesan.error = "masukkan nomer yang benar"
            et_notel_pemesan.requestFocus()
            return

        }
        if (et_notel_pemesan.text.toString().length >14){
            et_notel_pemesan.error = "Nomer yang anda masukkan terlallu banyak"
            et_notel_pemesan.requestFocus()
            return
        }


        val id = ref.push().key.toString()
        val nama = et_nama_pemesan.text.toString().trim()
        val alamat = et_alamat_pemesan.text.toString().trim()
        val paket = paket.text.toString().trim()
        val harga = total_harga.text.toString().trim()
        val nomer = et_notel_pemesan.text.toString().trim()

        val simpan = Pemesantest(id,nama,alamat,paket,harga,nomer)

        ref.child(id).setValue(simpan).addOnCompleteListener {
            Toast.makeText(this, "Sukses Pesan Data", Toast.LENGTH_SHORT).show()
            Log.d("test", ref.toString())
            startActivity(Intent(this, HomePemesan::class.java))
        }
    }
}