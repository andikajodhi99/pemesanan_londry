package com.example.pemesananlondry

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesananlondry.pemesan.HomePemesan
import com.example.pemesananlondry.pemesan.Pemesan
import com.example.pemesananlondry.pemesan.RegistrasiPemesan
import com.example.pemesananlondry.pengelola.LoginPengelola
import com.example.pemesananlondry.pengelola.PesananDiterima.PesananDiterimaPemilik
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_pengelola.*


class MainActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("Pemesan")
        //Fungsi Login

        val database = FirebaseDatabase.getInstance()
        //val myRef = database.getReference("pesan")
        btn_masuk_login.setOnClickListener {
            login()
        }
        //Fungsi daftar
        btn_daftar_pemesan.setOnClickListener {
            val intent = Intent(this, RegistrasiPemesan::class.java)
            startActivity(intent)
        }
        //Fungsi Login Pengelola
        btn_Pengelola.setOnClickListener {
            val intent = Intent(this, LoginPengelola::class.java)
            startActivity(intent)
        }
        btn_Lihatpemesan.setOnClickListener {
            if(btn_Lihatpemesan.text.toString().equals("Lihat")){
                et_passwd_pengelola.transformationMethod = HideReturnsTransformationMethod.getInstance()
                btn_Lihatpemesan.text = "Tutup"
            } else{
                et_passwd_pengelola.transformationMethod = PasswordTransformationMethod.getInstance()
                btn_Lihatpemesan.text = "Lihat"
            }
        }
    }

    private fun login() {
        //Falidasi
        if (et_email_pengelola.text.toString().isEmpty()) {
            et_email_pengelola.error = "Masukkan Email"
            et_email_pengelola.requestFocus()
            return
        }
        //Validasi Email jika input tidak sesuai
        if (!Patterns.EMAIL_ADDRESS.matcher(et_email_pengelola.text.toString()).matches()) {
            et_email_pengelola.error = "Masukkan Email Valid"
            et_email_pengelola.requestFocus()
        }
        if (et_passwd_pengelola.text.toString().isEmpty()) {
            et_passwd_pengelola.error = "Masukkan Email"
            et_passwd_pengelola.requestFocus()
            return
        }

        //deklarasi variabel yang digunakan
        val email =et_email_pengelola.text.toString().trim()
        val pasword= et_passwd_pengelola.text.toString()

        //query ke database firebase table Admin dengan chilnya adalah email
        auth.signInWithEmailAndPassword(email, pasword).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d("Test", "signInWithEmail:success")
                val user: FirebaseUser = auth.getCurrentUser()!!
                updateUI(user)
            } else {
                updateUI(null)
            }
        }
    }
    //fungsi untuk Verifikasi Email
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    fun getUser(email: String){
        var user: Pemesan? = null
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snap in p0.children) {
                    user = snap.getValue(Pemesan::class.java)
                }
            }
        })
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            if (currentUser.isEmailVerified){
                getUser(currentUser.email!!)
                startActivity(Intent(this, HomePemesan::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "Email belum Di Verifikasi silahkan cek Email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            Toast.makeText(
                baseContext, "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}