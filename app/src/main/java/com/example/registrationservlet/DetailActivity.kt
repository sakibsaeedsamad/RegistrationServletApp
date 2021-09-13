package com.example.registrationservlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        val intent = intent
        tvName.setText(intent.extras!!.getString("name"))
        tvMobile.setText(intent.extras!!.getString("mobile"))
        tvEmail.setText(intent.extras!!.getString("email"))
        tvAge.setText(intent.extras!!.getString("age"))
        tvGender.setText(intent.extras!!.getString("gender"))
        tvAddress.setText(intent.extras!!.getString("address"))
        tvRole.setText(intent.extras!!.getString("role"))

    }
}