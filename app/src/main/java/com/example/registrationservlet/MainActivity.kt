package com.example.registrationservlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registrationservlet.model.InsertModel
import com.example.registrationservlet.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.R.id

import android.content.Intent


class MainActivity : AppCompatActivity() {


    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        etName.setText("SAKIB")
        etMobile.setText("01933575667")
        etEmail.setText("sssakib@gmail.com")
        etDob.setText("12/02/1997")
        etGender.setText("Male")
        etAddress.setText("Dhaka")
        etRole.setText("Trainee")


        btnRegister.setOnClickListener {
            doInsert()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        userViewModel.userInsert_response_error.observe(this, androidx.lifecycle.Observer {
            it?.let {


                Log.e("Login-->", "Error Found")

            }
        })

        userViewModel.userInsert_response.observe(this, androidx.lifecycle.Observer {
            it?.let {

                if ("E".equals(it.errorCode)) {

                    Log.d("MessageD ", it.errorMessage.toString())
                    Toast.makeText(
                        this,
                        "Message : " + it.errorMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()




                }

                else{
                    val i = Intent(this, DetailActivity::class.java)
                    i.putExtra("name", it.name)
                    i.putExtra("mobile", it.mobile)
                    i.putExtra("email", it.email)
                    i.putExtra("dob", it.dob)
                    i.putExtra("gender", it.gender)
                    i.putExtra("address", it.address)
                    i.putExtra("role", it.role)
                    startActivity(i)
                }


                //Log.e("Login-->", "Error Found")

            }
        })
    }

    private fun doInsert() {
        var model = InsertModel()
        model.requestCode = ("1")
        model.name = etName.getText().toString().trim()
        model.mobile = etMobile.getText().toString().trim()
        model.email = etEmail.getText().toString().trim()
        model.dob = etDob.getText().toString().trim()
        model.gender = etGender.getText().toString().trim()
        model.address = etDob.getText().toString().trim()
        model.role = etRole.getText().toString().trim()



        this.let { it1 -> userViewModel.doInsert(model) }
    }
}