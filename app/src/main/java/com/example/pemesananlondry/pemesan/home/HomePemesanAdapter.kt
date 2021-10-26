package com.example.pemesananlondry.pemesan.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesananlondry.R


//Dimasukkan
class HomePemesanAdapter(private val context: Context, private val homepemesan:List<HomePemesanModel>)
    :RecyclerView.Adapter<HomePemesanAdapter.HomePemesanHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePemesanAdapter.HomePemesanHolder {
        val view: View= LayoutInflater.from(parent.context).inflate(
                R.layout.list_home_pemesan, parent, false
        )
        return HomePemesanHolder(view)
    }

    override fun onBindViewHolder(holder: HomePemesanAdapter.HomePemesanHolder, position: Int) {
        val list= homepemesan[position]
        holder.tv_alamat.text =list.alamat
        holder.tv_name.text = list.name
    }

    override fun getItemCount(): Int =homepemesan.size

    inner class HomePemesanHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_alamat: TextView = view.findViewById(R.id.tv_alamatpemesan)
        val tv_name: TextView = view.findViewById(R.id.tv_namehomepemesan)

    }
}