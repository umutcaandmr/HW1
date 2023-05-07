package com.umutdemir.ytumezun.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.databinding.DuyuruRecyclerRowBinding
import com.umutdemir.ytumezun.model.Duyuru
import com.umutdemir.ytumezun.model.Kullanici
import com.umutdemir.ytumezun.view.DuyuruDetayActivity

class DuyuruRecyclerAdapter(val context: Context, val duyuruList: ArrayList<Duyuru>) :
    RecyclerView.Adapter<DuyuruRecyclerAdapter.VH>() {

    class VH(val itemBinding: DuyuruRecyclerRowBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val db = Firebase.firestore
    private val user = Firebase.auth.currentUser
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            DuyuruRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val aciklama: String
        if (duyuruList[position].aciklama.length > 100) {
            aciklama = duyuruList[position].aciklama.substring(0, 100) + "..."
        } else {
            aciklama = duyuruList[position].aciklama
        }
        holder.itemBinding.recyclerAciklama.text = aciklama
        holder.itemBinding.recyclerBaslik.text = duyuruList[position].baslik
        holder.itemBinding.recyclerTarih.text = duyuruList[position].tarih
        db.collection("Kullanici").document(user!!.uid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userInfo = documentSnapshot.toObject(Kullanici::class.java)
                    val tamIsim = "${userInfo!!.isim} ${userInfo.soyisim}"
                    holder.itemBinding.recyclerIsim.text = tamIsim
                }
            }
        val query = db.collection("Duyuru").whereEqualTo("paylasanMail", duyuruList[position].paylasanMail)
                .whereEqualTo("paylasmaTarihi", duyuruList[position].paylasmaTarihi)
        var duyuruId = ""
        query.get().addOnSuccessListener {

            for(i in it){
                duyuruId = i.id
            }
        }

        holder.itemBinding.araCardView.setOnClickListener {
            val intent = Intent(context, DuyuruDetayActivity::class.java)
            intent.putExtra("id",duyuruId)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return duyuruList.size
    }


}