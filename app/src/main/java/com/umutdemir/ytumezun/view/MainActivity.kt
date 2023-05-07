package com.umutdemir.ytumezun.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            println("id :${intent.getStringExtra("id")}")
            when (intent.getStringExtra("id")) {
                "giris"-> {
                    loadFragment(AnasayfaFragment())
                    binding.navigationBar.selectedItemId = R.id.menuAnasayfa
                }
                "duyuru"->{
                    loadFragment(DuyuruFragment())
                    binding.navigationBar.selectedItemId = R.id.menuDuyuru
                }
                "anasayfa"->{
                    loadFragment(AnasayfaFragment())
                    binding.navigationBar.selectedItemId = R.id.menuAnasayfa
                }
                "profil"->{
                    loadFragment(ProfilFragment())
                    binding.navigationBar.selectedItemId = R.id.menuProfil
                }
                "ara"->{
                    loadFragment(AraFragment())
                    binding.navigationBar.selectedItemId = R.id.menuAra
                }

            }


        binding.navigationBar.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.menuProfil -> {
                    loadFragment(ProfilFragment())
                    true
                }
                R.id.menuDuyuru -> {
                    loadFragment(DuyuruFragment())
                    true
                }
                R.id.menuAnasayfa -> {
                    loadFragment(AnasayfaFragment())
                    true
                }
                R.id.menuAra -> {
                    loadFragment(AraFragment())
                    true
                }

                else -> false
            }
        }
    }

    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}