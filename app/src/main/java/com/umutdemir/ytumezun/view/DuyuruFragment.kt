package com.umutdemir.ytumezun.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.adapter.DuyuruRecyclerAdapter
import com.umutdemir.ytumezun.databinding.FragmentDuyuruBinding
import com.umutdemir.ytumezun.model.Duyuru

class DuyuruFragment : Fragment() {

    private lateinit var binding : FragmentDuyuruBinding
    private lateinit var duyuruListesi: ArrayList<Duyuru>
    private lateinit var adapter: DuyuruRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDuyuruBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = Firebase.firestore

        binding.duyuruEkleImage.setOnClickListener{
            val intent = Intent(requireContext(),DuyuruEkleActivity::class.java)
            startActivity(intent)
        }

        duyuruListesi = arrayListOf()
        binding.duyuruRecyclerView.setHasFixedSize(true)
        binding.duyuruRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val query = db.collection("Duyuru").orderBy("paylasmaTarihi", Query.Direction.DESCENDING)
        query.get().addOnSuccessListener { documents->
            duyuruListesi.clear()
            for(document in documents){
                val duyuru = document.toObject(Duyuru::class.java)
                duyuruListesi.add(duyuru)
            }

            adapter = DuyuruRecyclerAdapter(requireContext(),duyuruListesi)
            binding.duyuruRecyclerView.adapter = adapter
        }
        super.onViewCreated(view, savedInstanceState)
    }


}