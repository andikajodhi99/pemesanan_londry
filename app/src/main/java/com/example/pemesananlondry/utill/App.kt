package com.example.pemesananlondry.utill

import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesananlondry.MainActivity
import com.example.pemesananlondry.pemesan.HomePemesan
import com.example.pemesananlondry.pemesan.Pemesan
import com.example.pemesananlondry.pemesan.Pemesantest
import com.example.pemesananlondry.pengelola.HomePengelola
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email_pengelola
import kotlinx.android.synthetic.main.activity_login.et_passwd_pengelola
import kotlinx.android.synthetic.main.activity_registrasi.*
import kotlinx.android.synthetic.main.activity_registrasipengelola.*


class App:AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var ref : DatabaseReference
    fun logout(){
        auth.signOut()
    }
    fun login(emai:TextView, passwd: TextView){

        auth = FirebaseAuth.getInstance()
        if (emai.text.toString().isEmpty()) {
            emai.error = "Masukkan Email"
            emai.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emai.text.toString()).matches()) {
            emai.error = "Masukkan Email Valid"
            emai.requestFocus()
        }
        if (passwd.text.toString().isEmpty()) {
            passwd.error = "Masukkan Email"
            passwd.requestFocus()
            return
        }

        //deklarasi variabel yang digunakan
        val email =emai.text.toString().trim()
        val pasword= passwd.text.toString()

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
                startActivity(Intent(this, HomePengelola::class.java))
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