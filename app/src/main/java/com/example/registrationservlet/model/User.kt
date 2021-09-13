package com.example.registrationservlet.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("name")
    var name: String?,

    @SerializedName("mobile")
    var mobile: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("dob")
    var dob: String?,

    @SerializedName("gender")
    var gender: String?,

    @SerializedName("address")
    var address: String?,

    @SerializedName("role")
    var role: String?,


    @SerializedName("errorCode")
    var errorCode: String?,

    @SerializedName("errorMessage")
    var errorMessage: String?
)
