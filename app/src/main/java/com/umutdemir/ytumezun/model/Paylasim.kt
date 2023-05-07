package com.umutdemir.ytumezun.model

import android.net.Uri

data class Paylasim(
    var paylasanIsim : String = "",
    var paylasanMail : String = "",
    var paylasmaTarihi : com.google.firebase.Timestamp? = null,
    var paylasimAciklama : String = "",
    var paylasimDosyalar : ArrayList<String> = arrayListOf(),
    var paylasanFotograf : String = ""
)
