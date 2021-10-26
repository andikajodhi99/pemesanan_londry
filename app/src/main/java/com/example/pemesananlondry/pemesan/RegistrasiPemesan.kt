package com.example.pemesananlondry.pemesan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesananlondry.MainActivity
import com.example.pemesananlondry.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_pesanan_pemesan.*
import kotlinx.android.synthetic.main.activity_registrasi.*


//Dimasukkan
class RegistrasiPemesan : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("User")
        btn_daftarpemesan.setOnClickListener {
            registrasi()
        }
    }

    private fun registrasi() {
        //Proses Valiidasi
        if (et_email_anggota.text.toString().isEmpty()) {
            et_email_anggota.error = "Masukkan Email"
            et_email_anggota.requestFocus()
            return

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(et_email_anggota.text.toString()).matches()) {
            et_email_anggota.error = "Masukkan Email Valid"
            et_email_anggota.requestFocus()
        }
        if (et_nama_registrasi.text.toString().isEmpty()) {
            et_nama_registrasi.error = "Masukkan Nama"
            et_nama_registrasi.requestFocus()
            return
        }
        if (et_alamat_registrasi.text.toString().isEmpty()) {
            et_alamat_registrasi.error = "Masukkan Alamat"
            et_alamat_registrasi.requestFocus()
            return
        }
        if (et_passwd_anggota.text.toString().isEmpty()) {
            et_passwd_anggota.error = "Masukkan Password"
            et_passwd_anggota.requestFocus()
            return
        }


//        Log.d("test1",et_email_anggota.text.toString())
//        Log.d("test2",et_alamat_registrasi.text.toString())
//        Log.d("test3",et_nama_registrasi.text.toString())


        val email = et_email_anggota.text.toString().trim()
        val pass = et_passwd_anggota.text.toString().trim()

        //Query database
        auth.createUserWithEmailAndPassword(
                email, pass
        ).addOnCompleteListener(this@RegistrasiPemesan) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.sendEmailVerification()
                        ?.addOnCompleteListener { //task ->
                            Log.d("test",user.toString())
                            if (task.isSuccessful) {
                                Toast.makeText(
                                        baseContext,
                                        "Silahkan Cek Email",
                                        Toast.LENGTH_SHORT
                                ).show()
                                simpanFirebase()
                            }
                        }
            } else {
                Toast.makeText(
                        baseContext, "Ulang Lagi",
                        Toast.LENGTH_SHORT
                ).show()
            }
            //Firebase database
        }

    }

    //Dimasukkan
    private fun simpanFirebase() {

        val id = ref.push().key.toString()
        val nama = et_nama_registrasi.text.toString().trim()
        val alamat = et_alamat_registrasi.text.toString().trim()
        val email = et_email_anggota.text.toString().trim()
        val passwd = et_passwd_anggota.text.toString().trim()

        val simpan = Pemesan(id,nama,email, passwd, alamat)

        //query aatabase
        ref.child(id).setValue(simpan).addOnCompleteListener {
            Toast.makeText(this, "Sukses Pesan Data", Toast.LENGTH_SHORT).show()
            Log.d("test", ref.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}