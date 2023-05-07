package com.umutdemir.ytumezun.model

data class Kullanici(
    var isim: String = "",
    var soyisim: String = "",
    var mail: String = "",
    var girisYil: String = "",
    var mezunYil: String = "",
    var bolum: String = "",
    var fotografUrl: String = "https://firebasestorage.googleapis.com/v0/b/ytu-mezun.appspot.com/o/fotograflar%2Fprofile.png?alt=media&token=f93a043a-9706-4c1c-9f85-a8a1719d745c",
    var derece: String = "",
    var numara: String = "",
    var ulke: String = "",
    var sehir: String = "",
    var takipciSayi: Int = 0,
    var takipEdilenSayi: Int = 0,
)
