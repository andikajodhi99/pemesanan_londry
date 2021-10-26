package com.example.pemesananlondry.pengelola.Pesanan

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.HomePemesan
import com.example.pemesananlondry.pemesan.Pemesantest
import com.example.pemesananlondry.pengelola.HomePengelola
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pesanan_pemesan.*



//Dimasukkan
class PesananAdapter(private val context: Context, private val pesanan:List<PesananModel>)
    :RecyclerView.Adapter<PesananAdapter.PesananHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananAdapter.PesananHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(
                R.layout.list_pesanan_masuk, parent, false
        )
        return PesananHolder(view)
    }
    override fun onBindViewHolder(holder: PesananAdapter.PesananHolder, position: Int) {
        val list= pesanan[position]
        holder.list_nama.text = list.nama
        holder.list_alamat.text= list.alamat
        holder.list_paket.text = list.paket
        holder.btn_listambil.setOnClickListener {
            val nama = list.alamat
            val  i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/$nama"))
            context.startActivity(i)

            //Simpan baru ke Pesanan diamabil
            val ref = FirebaseDatabase.getInstance().getReference("DataPesanDiambil").child(list.id)
            val id = list.id
            val namaa= list.nama
            val alamat = list.alamat
            val paket = list.paket
            val harga = list.harga
            val nomer = list.notel
            val simpan = PesananModel(id,namaa,alamat,paket,harga,nomer)
            ref.setValue(simpan).addOnCompleteListener {
                Toast.makeText(context, "Suksses Pesanan diambil", Toast.LENGTH_SHORT).show()
                //Log.d("test", ref.toString())
            }

           // Hapus dari dataPesan
            val refhapus = FirebaseDatabase.getInstance().getReference("DataPesan").child(list.id)
            val Query: Query = refhapus
            Query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (appleSnapshot in dataSnapshot.children) {
                        appleSnapshot.ref.removeValue()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(ContentValues.TAG, "onCancelled", databaseError.toException())
                }
            })
            val intent = Intent(context, HomePengelola::class.java)
            context.startActivity(intent)

        }
        holder.btn_listchat.setOnClickListener {
            val nama = list.alamat
            val  i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/$nama"))
           // val  i = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=62${list.notel}&text=Segera%20Meluncur"))
            context.startActivity(i)
        }
//        holder.btn_listhapus.setOnClickListener {
//            val ref = FirebaseDatabase.getInstance().getReference("DataPesan").child(list.id)
//            val Query: Query = ref
//            Query.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (appleSnapshot in dataSnapshot.children) {
//                        appleSnapshot.ref.removeValue()
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.e(ContentValues.TAG, "onCancelled", databaseError.toException())
//                }
//            })
//            val intent = Intent(context, HomePengelola::class.java)
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int =pesanan.size

    inner class PesananHolder(view: View) : RecyclerView.ViewHolder(view) {
        val list_paket: TextView = view.findViewById(R.id.list_paket)
        val list_alamat: TextView = view.findViewById(R.id.list_alamat)
        val list_nama: TextView = view.findViewById(R.id.list_nama)

        //val btn_listhapus:Button = view.findViewById(R.id.btn_listhapus)
        val btn_listchat:Button = view.findViewById(R.id.btn_listchat)
        val btn_listambil:Button = view.findViewById(R.id.btn_listambil)
    }
}