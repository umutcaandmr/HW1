package com.umutdemir.ytumezun.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.databinding.DuyuruRecyclerRowBinding
import com.umutdemir.ytumezun.databinding.PaylasimRecyclerRowBinding
import com.umutdemir.ytumezun.model.Duyuru
import com.umutdemir.ytumezun.model.Kullanici
import com.umutdemir.ytumezun.model.Paylasim
import com.umutdemir.ytumezun.view.DuyuruDetayActivity

class PaylasimRecyclerAdapter(val context: Context, val paylasimList: ArrayList<Paylasim>) :
    RecyclerView.Adapter<PaylasimRecyclerAdapter.VH>() {

    class VH(val itemBinding: PaylasimRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val db = Firebase.firestore
    private val user = Firebase.auth.currentUser
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            PaylasimRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {


        holder.itemBinding.paylasimAciklama.text = paylasimList[position].paylasimAciklama
        holder.itemBinding.isim.text = paylasimList[position].paylasanIsim
        Picasso.get().load(paylasimList[position].paylasanFotograf).into(holder.itemBinding.profilFotografi)


    }

    override fun getItemCount(): Int {
        return paylasimList.size
    }


}