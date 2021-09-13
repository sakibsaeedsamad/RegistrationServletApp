package com.example.registrationservlet.model

import com.google.gson.annotations.SerializedName


data class Role(
    @SerializedName("code")
    var code: String?,

    @SerializedName("desc")
    var desc: String?

)
