package com.umutdemir.ytumezun.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.databinding.ProfilAraRecyclerRowBinding
import com.umutdemir.ytumezun.model.Kullanici
import com.umutdemir.ytumezun.view.ProfilActivity


class ProfilAraRecyclerAdapter(val kullaniciList: ArrayList<Kullanici>) :
    RecyclerView.Adapter<ProfilAraRecyclerAdapter.VH>(), Filterable {
    var filteredData = ArrayList<Kullanici>()
    lateinit var context: Context

    init {
        filteredData = kullaniciList
    }

    class VH(val itemBinding: ProfilAraRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        context = parent.context
        return VH(
            ProfilAraRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val kullanici = filteredData[position]
        val tamIsim = kullanici.isim + " " + kullanici.soyisim
        holder.itemBinding.recyclerIsim.text = tamIsim

        holder.itemBinding.recyclerMail.text = kullanici.mail
        val yil =  "(" + kullanici.girisYil + "-" + kullanici.mezunYil + ")"
        holder.itemBinding.recyclerYil.text = yil

        Picasso.get().load(kullanici.fotografUrl).resize(180, 180)
            .into(holder.itemBinding.recyclerProfilFotografi)
        holder.itemBinding.recyclerBolum.text = kullanici.bolum
        holder.itemBinding.recyclerDerece.text = kullanici.derece

        holder.itemBinding.araCardView.setOnClickListener {
            val intent = Intent(context, ProfilActivity::class.java)
            intent.putExtra("mail",kullanici.mail)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString()

                if (query.isEmpty()) {
                    filteredData = kullaniciList
                } else {
                    val filteredList = ArrayList<Kullanici>()
                    for (item in kullaniciList) {
                        if (item.isim.lowercase().contains(query) || item.soyisim.lowercase()
                                .contains(query) || item.mail.lowercase().contains(
                                query
                            ) || item.bolum.lowercase().contains(query) || item.ulke.lowercase()
                                .contains(query) || item.sehir.lowercase().contains(
                                query
                            ) || item.bolum.lowercase().contains(query) || item.numara.lowercase()
                                .contains(query)
                        ) {
                            filteredList.add(item)
                        }
                    }
                    filteredData = filteredList
                }

                val results = FilterResults()
                results.values = filteredData
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as ArrayList<Kullanici>
                notifyDataSetChanged()
            }
        }
    }

}