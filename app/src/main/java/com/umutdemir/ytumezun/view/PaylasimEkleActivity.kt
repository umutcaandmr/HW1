package com.umutdemir.ytumezun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.adapter.DuyuruRecyclerAdapter
import com.umutdemir.ytumezun.adapter.PaylasimDosyaRecyclerAdapter
import com.umutdemir.ytumezun.databinding.ActivityPaylasimEkleBinding
import com.umutdemir.ytumezun.model.Kullanici
import com.umutdemir.ytumezun.model.Paylasim
import java.util.UUID

class PaylasimEkleActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaylasimEkleBinding
    private lateinit var adapter : PaylasimDosyaRecyclerAdapter
    val dosyalar : ArrayList<Uri> = arrayListOf()
    @SuppressLint("NotifyDataSetChanged")
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
            /*
                val storage = Firebase.storage
                val reference = storage.reference
                val uuid = UUID.randomUUID()
                val imageName = "${uuid}.jpg"
                val imageReference = reference.child("Paylasim").child(imageName)
                imageReference.putFile(uri).addOnSuccessListener {
                    imageReference.downloadUrl.addOnSuccessListener {
                        val downloadUrl = it.toString()
                        dosyalar.add(downloadUrl)
                        adapter = PaylasimDosyaRecyclerAdapter(this,dosyalar)
                        adapter.notifyDataSetChanged()
                        binding.paylasimDosyaRecyclerView.adapter= adapter
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
             */

                dosyalar.add(uri)
                adapter = PaylasimDosyaRecyclerAdapter(this,dosyalar)
                adapter.notifyDataSetChanged()
                binding.paylasimDosyaRecyclerView.adapter= adapter
                println(dosyalar)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaylasimEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paylasimDosyaRecyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = PaylasimDosyaRecyclerAdapter(this,dosyalar)
        adapter.notifyDataSetChanged()
        binding.paylasimDosyaRecyclerView.adapter= adapter

        binding.paylasButon.setOnClickListener {
            val user = Firebase.auth.currentUser
            val uriList = arrayListOf<String>()
            for(uri in dosyalar){
                uriList.add(uri.toString())
            }

            for(uri in dosyalar){
                val storage = Firebase.storage
                val reference = storage.reference
                val uuid = UUID.randomUUID()
                val imageName = "${uuid}.jpg"

                val imageReference = reference.child("Paylasim").child(imageName)
                imageReference.putFile(uri).addOnSuccessListener {
                    imageReference.downloadUrl.addOnSuccessListener {

                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            val db = Firebase.firestore
            db.collection("Kullanici").document(user!!.uid).get().addOnSuccessListener {
                val kullanici = it.toObject(Kullanici::class.java)!!
                val isimFormat = kullanici.isim + " " + kullanici.soyisim
                val paylasim = Paylasim(isimFormat,kullanici.mail,com.google.firebase.Timestamp.now(),binding.baslikEditText.text.toString(),uriList,kullanici.fotografUrl)

                db.collection("Paylasim").add(paylasim).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this,getString(R.string.successfulPost),Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("id","anasayfa")
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { e ->
                    println("db yukleme : ${e.message}")
                }

            }


        }
        binding.geriButon.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("id","anasayfa")
            startActivity(intent)
            finish()
        }

        binding.fotografEkle.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

    }
}