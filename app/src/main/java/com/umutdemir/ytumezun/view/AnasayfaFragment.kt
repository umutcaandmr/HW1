package com.umutdemir.ytumezun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.umutdemir.ytumezun.adapter.PaylasimDosyaRecyclerAdapter
import com.umutdemir.ytumezun.adapter.PaylasimRecyclerAdapter
import com.umutdemir.ytumezun.databinding.FragmentAnasayfaBinding
import com.umutdemir.ytumezun.databinding.FragmentDuyuruBinding
import com.umutdemir.ytumezun.model.Duyuru
import com.umutdemir.ytumezun.model.Kullanici
import com.umutdemir.ytumezun.model.Paylasim

class AnasayfaFragment : Fragment() {

    private lateinit var paylasimListAdapter : PaylasimRecyclerAdapter
    private lateinit var paylasimFotografAdapter : PaylasimDosyaRecyclerAdapter

    private lateinit var binding: FragmentAnasayfaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAnasayfaBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val paylasimList = ArrayList<Paylasim>()
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val storage = Firebase.storage
        val reference = storage.reference

        db.collection("Paylasim").orderBy("paylasmaTarihi",Query.Direction.DESCENDING).get().addOnSuccessListener {
            for(duyuru in it){
                paylasimList.add(duyuru.toObject(Paylasim::class.java))
            }




            paylasimListAdapter = PaylasimRecyclerAdapter(requireContext(),paylasimList)
            paylasimListAdapter.notifyDataSetChanged()
            binding.duyuruRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.duyuruRecyclerView.adapter = paylasimListAdapter
        }

        binding.duyuruEkleImage.setOnClickListener {
            val intent = Intent(requireActivity(),PaylasimEkleActivity::class.java)
            startActivity(intent)
        }
    }

}