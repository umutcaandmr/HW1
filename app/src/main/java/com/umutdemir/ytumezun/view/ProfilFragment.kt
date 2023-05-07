package com.umutdemir.ytumezun.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.FragmentProfilDuzenleBinding
import com.umutdemir.ytumezun.model.Kullanici


class ProfilFragment : Fragment() {
    private lateinit var kullanici:Kullanici
    private lateinit var binding : FragmentProfilDuzenleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfilDuzenleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = Firebase.auth
        val db = Firebase.firestore

        db.collection("Kullanici").whereEqualTo("mail",user.currentUser!!.email).get().addOnSuccessListener {
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










        super.onViewCreated(view, savedInstanceState)
        binding.profilGuncelle.visibility = View.VISIBLE
        binding.cikisYap.setOnClickListener {
            user.signOut()
            val intent = Intent(requireContext(),GirisActivity::class.java)
            startActivity(intent)

        }
        binding.profilGuncelle.setOnClickListener {
            val intent = Intent(requireContext(),ProfilGuncelleActivity::class.java)
            startActivity(intent)
        }
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
    }


}