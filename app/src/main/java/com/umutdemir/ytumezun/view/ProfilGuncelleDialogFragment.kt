package com.umutdemir.ytumezun.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ProfilGuncelleDialogBinding
import com.umutdemir.ytumezun.model.Kullanici

class ProfilGuncelleDialogFragment : DialogFragment() {

    private lateinit var binding: ProfilGuncelleDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = ProfilGuncelleDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser


        val query = db.collection("Kullanici").whereEqualTo("mail", user!!.email)
        query.get().addOnSuccessListener {documents->
            for (i in documents){
                val userInfo = i.toObject<Kullanici>()
                binding.nameEditText.setText(userInfo.isim)
                binding.surnameEditText.setText(userInfo.soyisim)
                binding.mailEditText.setText(userInfo.mail)

            }
        }

        binding.kaydetButon.setOnClickListener {

            val query = db.collection("Kullanici").whereEqualTo("mail", user!!.email)

            query.get().addOnSuccessListener {
                for (i in it) {
                    db.collection("Kullanici").document(i.id)
                        .update(
                            "isim",
                            binding.nameEditText.text.toString(),
                            "soyisim",
                            binding.surnameEditText.text.toString(),
                            "mail",
                            binding.mailEditText.text.toString()
                        )
                }
            }
            dismiss()

        }
        super.onViewCreated(view, savedInstanceState)
    }

}