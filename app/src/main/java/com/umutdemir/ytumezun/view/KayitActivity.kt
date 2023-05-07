package com.umutdemir.ytumezun.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ActivityKayitBinding


class KayitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKayitBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKayitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.geriButon.setOnClickListener {
            val intent = Intent(this,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.button.setOnClickListener {
            boslukKontrol()
        }
    }

    fun boslukKontrol() {
        var ok = 1
        val editTexts = arrayOf(
            binding.nameEditText,
            binding.surnameEditText,
            binding.mailEditText,
            binding.passwordEditText,
            binding.passwordConfirmEditText,
            binding.entryYearEditText,
            binding.graduateYearEditText

        )
        val textLayouts = arrayOf(
            binding.nameTextLayout,
            binding.surnameTextLayout,
            binding.mailTextLayout,
            binding.passwordTextLayout,
            binding.passwordConfirmTextLayout,
            binding.entryYearTextLayout,
            binding.graudateYearTextLayout
        )
        for (i in editTexts.indices) {
            if (editTexts[i].text.toString().isEmpty()) {
                textLayouts[i].helperText = getString(com.umutdemir.ytumezun.R.string.required)
                ok = 0
            } else {
                textLayouts[i].helperText = null
            }
        }
        if (ok == 1) {
            if (degerKontrol()) {
                kaydol()
            }
        }
    }

    fun degerKontrol(): Boolean {
        var ok = true
        if (!binding.mailEditText.text!!.contains("std.yildiz.edu.tr")) {
            binding.mailTextLayout.helperText =
                getString(com.umutdemir.ytumezun.R.string.emailnotvalid)
            ok = false
        }
        if (binding.passwordEditText.text.toString() != binding.passwordConfirmEditText.text.toString()) {
            binding.passwordTextLayout.helperText =
                getString(com.umutdemir.ytumezun.R.string.password_problem)
            binding.passwordConfirmTextLayout.helperText =
                getString(com.umutdemir.ytumezun.R.string.password_problem)
            ok = false
        } else if (!isValidPassword(binding.passwordEditText.text.toString())) {
            binding.passwordTextLayout.helperText =
                "Lütfen güçlü bir parola girin(En az 6 karakter,Büyük harf,Küçük harf,Sayı,Özel karakter içermeli)"
            ok = false
        }
        if (binding.entryYearEditText.text.toString().length != 4 || binding.entryYearEditText.text.toString()
                .toInt() >= binding.graduateYearEditText.text.toString().toInt()

        ) {
            binding.entryYearTextLayout.helperText =
                getString(com.umutdemir.ytumezun.R.string.year_error)
            ok = false
        }
        return ok
    }

    fun isValidPassword(password: String?): Boolean {
        password?.let {
            if (it.length < 6) {
                return false
            }
            val passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=-])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }


    fun kaydol() {
        println("mail : ${binding.mailEditText.text.toString()}")
        println("sifre : ${binding.passwordEditText.text.toString()}")

        auth.createUserWithEmailAndPassword(
            binding.mailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    println("email dogrulama : ${user!!.isEmailVerified}")
                    user.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                println("Email gonderildi")
                            }
                        }

                    val db = Firebase.firestore
                    val kullaniciHashMap = hashMapOf<String, String>()
                    kullaniciHashMap["isim"] = binding.nameEditText.text.toString()
                    kullaniciHashMap["soyisim"] = binding.surnameEditText.text.toString()
                    kullaniciHashMap["mail"] = binding.mailEditText.text.toString()
                    kullaniciHashMap["girisYil"] = binding.entryYearEditText.text.toString()
                    kullaniciHashMap["mezunYil"] = binding.graduateYearEditText.text.toString()

                    db.collection("Kullanici")
                        .document(user.uid).set(kullaniciHashMap).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this,getString(R.string.successfulRegistration),Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, GirisActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }.addOnFailureListener { e ->
                            println("db yukleme : ${e.message}")
                        }


                } else {
                    // If sign in fails, display a message to the user.
                    println("createUserWithEmail:failure : ${task.exception}")
                    Toast.makeText(
                        baseContext,
                        "Kayit Hatasi",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}