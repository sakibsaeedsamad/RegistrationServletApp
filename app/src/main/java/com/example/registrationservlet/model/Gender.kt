package com.example.registrationservlet.model

import com.google.gson.annotations.SerializedName

data class Gender(
    @SerializedName("genCode")
    var genCode: String?,

    @SerializedName("genDesc")
    var genDesc: String?

)

