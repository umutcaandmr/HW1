package com.umutdemir.ytumezun.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.adapter.ProfilAraRecyclerAdapter
import com.umutdemir.ytumezun.databinding.FragmentAraBinding
import com.umutdemir.ytumezun.databinding.FragmentDuyuruBinding
import com.umutdemir.ytumezun.model.Duyuru
import com.umutdemir.ytumezun.model.Kullanici


class AraFragment : Fragment() {
    private lateinit var binding: FragmentAraBinding
    private lateinit var mezunListesi: ArrayList<Kullanici>
    private lateinit var adapter: ProfilAraRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mezunListesi = arrayListOf()

        val db = Firebase.firestore
        binding.araRecyclerView.setHasFixedSize(true)
        binding.araRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.araScrollView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        val query = db.collection("Kullanici")

        query.get().addOnSuccessListener { documents ->
            mezunListesi.clear()

            for (document in documents) {
                val kullanici = document.toObject(Kullanici::class.java)
                mezunListesi.add(kullanici)

            }
            val activity = requireActivity() as MainActivity
            adapter = ProfilAraRecyclerAdapter(mezunListesi)
            binding.araRecyclerView.adapter = adapter
            binding.araScrollView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return true
                }
            })
        }.addOnFailureListener { e ->
        }
        super.onViewCreated(view, savedInstanceState)
    }

}