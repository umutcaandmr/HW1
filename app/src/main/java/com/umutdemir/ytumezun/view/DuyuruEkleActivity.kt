package com.umutdemir.ytumezun.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.umutdemir.ytumezun.R
import com.umutdemir.ytumezun.databinding.ActivityDuyuruEkleBinding
import com.umutdemir.ytumezun.model.Duyuru
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DuyuruEkleActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityDuyuruEkleBinding
    private val calendar = Calendar.getInstance()
    private lateinit var tarih: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuyuruEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tarihEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        binding.geriButon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id","duyuru")
            startActivity(intent)
            finish()
        }

        binding.paylasButon.setOnClickListener {

            if (boslukKontrol()) {

                val db = Firebase.firestore
                val user = Firebase.auth.currentUser
                val duyuru = Duyuru(
                    binding.baslikEditText.text.toString(),
                    binding.tarihEditText.text.toString(),
                    binding.duyuruAciklamaEditText.text.toString(),
                    user!!.email.toString(),
                    com.google.firebase.Timestamp.now()
                )
                db.collection("Duyuru").add(duyuru).addOnSuccessListener {
                    Toast.makeText(this,getString(R.string.successfulAnnouncement),Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id","duyuru")
                    startActivity(intent)
                }


            }

        }


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        tarih = dateFormat.format(calendar.time)

        val gun = SimpleDateFormat("EEEE", Locale("tr")).format(calendar.time)
        val formattedTurkishDate = "$tarih, $gun"

        binding.tarihEditText.setText(formattedTurkishDate)
    }

    fun boslukKontrol(): Boolean {
        var ok = true
        val editTexts = arrayOf(
            binding.baslikEditText,
            binding.tarihEditText,
            binding.duyuruAciklamaEditText,

            )
        val textLayouts = arrayOf(
            binding.baslikTextLayout,
            binding.tarihTextLayout,
            binding.duyuruAciklamaTextLayout,

            )
        for (i in editTexts.indices) {
            if (editTexts[i].text.toString().isEmpty()) {
                textLayouts[i].helperText = getString(com.umutdemir.ytumezun.R.string.required)
                ok = false
            } else {
                textLayouts[i].helperText = null
            }
        }
        return ok
    }
}