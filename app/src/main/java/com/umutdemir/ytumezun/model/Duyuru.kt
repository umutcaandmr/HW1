package com.umutdemir.ytumezun.model

import java.util.Date


data class Duyuru(
    var baslik : String = "",
    var tarih : String = "",
    var aciklama : String = "",
    var paylasanMail : String = "",
    var paylasmaTarihi : com.google.firebase.Timestamp? = null

)
