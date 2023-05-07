package com.umutdemir.ytumezun.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ActivityGirisBinding

class GirisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGirisBinding
    private lateinit var auth: FirebaseAuth
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id","giris")
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registerText.setOnClickListener {
            val intent = Intent(this, KayitActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            if (binding.mailEditText.text.isNullOrEmpty() || binding.passwordEditText.text.isNullOrEmpty()) {
                Toast.makeText(this, R.string.emptyLogin, Toast.LENGTH_SHORT).show()
            } else {

                if (!binding.mailEditText.text!!.contains("std.yildiz.edu.tr")) {
                    binding.mailTextLayout.helperText =
                        getString(com.umutdemir.ytumezun.R.string.emailnotvalid)
                }
                else{
                    girisYap()
                }
            }
        }
    }

    fun girisYap() {
        auth.signInWithEmailAndPassword(
            binding.mailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id","giris")
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    println("signInWithEmail:failure : ${task.exception}")
                    Toast.makeText(
                        baseContext,
                        "Lütfen e-posta ve şifrenizi kontrol edin",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }



}
