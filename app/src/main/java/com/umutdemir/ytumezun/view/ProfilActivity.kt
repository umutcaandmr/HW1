package com.umutdemir.ytumezun.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ActivityProfilBinding
import com.umutdemir.ytumezun.model.Kullanici

class ProfilActivity : AppCompatActivity() {
    private lateinit var kullanici : Kullanici
    private lateinit var binding : ActivityProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val mail = intent.getStringExtra("mail")
        binding.profilBilgiImage.setOnClickListener {
            if(binding.profilBilgiLinear.visibility == View.GONE){
                binding.profilBilgiImage.setImageResource(R.drawable.ok_asagi)
                binding.profilBilgiLinear.visibility = View.VISIBLE
            }
            else{
                binding.profilBilgiImage.setImageResource(R.drawable.ok)
                binding.profilBilgiLinear.visibility = View.GONE
            }
        }

        binding.profilEgitimImage.setOnClickListener {
            if(binding.profilEgitimLinear.visibility == View.GONE){
                binding.profilEgitimImage.setImageResource(R.drawable.ok_asagi)
                binding.profilEgitimLinear.visibility = View.VISIBLE
            }
            else{
                binding.profilEgitimImage.setImageResource(R.drawable.ok)
                binding.profilEgitimLinear.visibility = View.GONE
            }
        }

        binding.profilIletisimImage.setOnClickListener {
            if(binding.profilIletisimLinear.visibility == View.GONE){
                binding.profilIletisimImage.setImageResource(R.drawable.ok_asagi)
                binding.profilIletisimLinear.visibility = View.VISIBLE
            }
            else{
                binding.profilIletisimImage.setImageResource(R.drawable.ok)
                binding.profilIletisimLinear.visibility = View.GONE
            }
        }
        db.collection("Kullanici").whereEqualTo("mail",mail).get().addOnSuccessListener {
            for(i in it){
                kullanici = i.toObject(Kullanici::class.java)
            }
            val isimFormat = binding.profilIsim.text.toString() + " : " + kullanici.isim
            binding.profilIsim.text = isimFormat

            val soyIsimFormat = binding.profilSoyIsim.text.toString() + " : " + kullanici.soyisim
            binding.profilSoyIsim.text = soyIsimFormat

            val dereceFormat = binding.profilDerece.text.toString() + " : " + kullanici.derece
            binding.profilDerece.text = dereceFormat

            val mailFormat = binding.profilMail.text.toString() + " : " + kullanici.mail
            binding.profilMail.text = mailFormat

            val ulkeFormat = binding.profilUlke.text.toString() + " : " + kullanici.ulke
            binding.profilUlke.text = ulkeFormat

            val sehirFormat = binding.profilSehir.text.toString() + " : " + kullanici.sehir
            binding.profilSehir.text = sehirFormat

            val numaraFormat = binding.profilNumara.text.toString() + " : " + kullanici.numara
            binding.profilNumara.text = numaraFormat

            val yilFormat = binding.profilYil.text.toString() + " : " + kullanici.girisYil + "-" + kullanici.mezunYil
            binding.profilYil.text = yilFormat


            Picasso.get().load(kullanici.fotografUrl).resize(200,200).into(binding.profilFotografi)
        }







    }
}