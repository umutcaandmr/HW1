package com.umutdemir.ytumezun.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ActivityDuyuruDetayBinding
import com.umutdemir.ytumezun.model.Duyuru

class DuyuruDetayActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDuyuruDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuyuruDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        val id = intent.getStringExtra("id")

        db.collection("Duyuru").document(id!!).get().addOnSuccessListener {
            val duyuruInfo = it.toObject<Duyuru>()
            binding.detayAciklama.text = duyuruInfo!!.aciklama
            binding.detayBaslik.text = duyuruInfo.baslik
            binding.detayPaylasan.text = duyuruInfo.paylasanMail
            binding.detayTarih.text = duyuruInfo.tarih
        }



        binding.geriButon.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("id","duyuru")
            startActivity(intent)
            finish()
        }
    }
}