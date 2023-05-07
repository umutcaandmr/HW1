package com.umutdemir.ytumezun.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.databinding.ActivityProfilGuncelleBinding
import com.umutdemir.ytumezun.model.Kullanici
import java.util.UUID

class ProfilGuncelleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilGuncelleBinding
    private lateinit var kullanici: Kullanici
    private lateinit var fotografUri: Uri
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val db = Firebase.firestore
                val user = Firebase.auth.currentUser
                val storage = Firebase.storage
                val reference = storage.reference
                val uuid = UUID.randomUUID()
                val imageName = "${uuid}.jpg"
                val imageReference = reference.child("fotograflar").child(imageName)
                imageReference.putFile(uri).addOnSuccessListener {
                    imageReference.downloadUrl.addOnSuccessListener {
                        val downloadUrl = it.toString()
                        db.collection("Kullanici")
                            .document(user!!.uid)
                            .update("fotografUrl", downloadUrl)
                        Picasso.get().load(uri).into(binding.profilFotografi)
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        db.collection("Kullanici").whereEqualTo("mail", user!!.email).get().addOnSuccessListener {
            for (i in it) {
                kullanici = i.toObject(Kullanici::class.java)
            }

            binding.nameEditText.setText(kullanici.isim)
            binding.surnameEditText.setText(kullanici.soyisim)
            binding.mailEditText.setText(kullanici.mail)
            binding.bolumEditText.setText(kullanici.bolum)
            binding.dereceEditText.setText(kullanici.bolum)
            binding.entryYearEditText.setText(kullanici.girisYil)
            binding.graduateYearEditText.setText(kullanici.mezunYil)
            binding.numaraEditText.setText(kullanici.numara)
            binding.ulkeEditText.setText(kullanici.ulke)
            binding.sehirEditText.setText(kullanici.sehir)
            Picasso.get().load(kullanici.fotografUrl).resize(200, 200).into(binding.profilFotografi)
            binding.profilFotografi.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

        }

        binding.button.setOnClickListener {
            db.collection("Kullanici").document(user.uid)
                .update(
                    "isim", binding.nameEditText.text.toString(),
                    "soyisim",binding.surnameEditText.text.toString(),
                    "mail", binding.mailEditText.text.toString(),
                    "girisYil", binding.entryYearEditText.text.toString(),
                    "mezunYil",binding.graduateYearEditText.text.toString(),
                    "bolum",binding.bolumEditText.text.toString(),
                    "derece",binding.dereceEditText.text.toString(),
                    "numara",binding.numaraEditText.text.toString(),
                    "ulke",binding.ulkeEditText.text.toString(),
                    "sehir",binding.sehirEditText.text.toString()
                ).addOnSuccessListener {
                    val intent = Intent(this,MainActivity::class.java)
                    intent.putExtra("id","profil")
                    startActivity(intent)
                    finish()
                }

        }
        binding.geriButon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", "profil")
            startActivity(intent)
            finish()
        }
    }


}