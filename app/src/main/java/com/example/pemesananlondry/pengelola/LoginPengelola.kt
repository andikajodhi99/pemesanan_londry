package com.example.pemesananlondry.pengelola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.Pemesan
import com.example.pemesananlondry.pengelola.PesananDiterima.PesananDiterimaPemilik
import com.example.pemesananlondry.utill.App
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_pengelola.*


//Dimasukkan
class LoginPengelola : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_pengelola)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("Pengelola")
        btn_masuk_pengelola.setOnClickListener {
           login()
        }
        btn_Lihat.setOnClickListener {
            if(btn_Lihat.text.toString().equals("Lihat")){
                et_passwd_pengelolalogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                btn_Lihat.text = "Tutup"
            } else{
                et_passwd_pengelolalogin.transformationMethod = PasswordTransformationMethod.getInstance()
                btn_Lihat.text = "Lihat"
            }
        }

//        btn_daftar_pengelola.setOnClickListener {
//            val intent = Intent(this, RegistrasiPengelola::class.java)
//            startActivity(intent)
//        }
    }


    private fun login() {

        if (et_email_pengelolalogin.text.toString().isEmpty()) {
            et_email_pengelolalogin.error = "Masukkan Email"
            et_email_pengelolalogin.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(et_email_pengelolalogin.text.toString()).matches()) {
            et_email_pengelolalogin.error = "Masukkan Email Valid"
            et_email_pengelolalogin.requestFocus()
        }
        if (et_passwd_pengelolalogin.text.toString().isEmpty()) {
            et_passwd_pengelolalogin.error = "Masukkan Password"
            et_passwd_pengelolalogin.requestFocus()
            return
        }

        //deklarasi variabel yang digunakan
        val email =et_email_pengelolalogin.text.toString().trim()
        val pasword= et_passwd_pengelolalogin.text.toString()

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
                startActivity(Intent(this, PesananDiterimaPemilik::class.java))
//                startActivity(Intent(this, HomePengelola::class.java))
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
