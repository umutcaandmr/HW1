package com.umutdemir.ytumezun.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.databinding.DuyuruRecyclerRowBinding
import com.umutdemir.ytumezun.databinding.PaylasimDosyaRecyclerRowBinding
import com.umutdemir.ytumezun.databinding.PaylasimRecyclerRowBinding
import com.umutdemir.ytumezun.model.Duyuru
import com.umutdemir.ytumezun.model.Kullanici
import com.umutdemir.ytumezun.view.DuyuruDetayActivity

class PaylasimDosyaRecyclerAdapter(val context: Context, val fotografList: ArrayList<Uri>) :
    RecyclerView.Adapter<PaylasimDosyaRecyclerAdapter.VH>() {

    class VH(val itemBinding: PaylasimDosyaRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val db = Firebase.firestore
    private val user = Firebase.auth.currentUser
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            PaylasimDosyaRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Picasso.get().load(fotografList[position])
            .into(holder.itemBinding.recyclerFotograf)
    }

    override fun getItemCount(): Int {
        return fotografList.size
    }


}