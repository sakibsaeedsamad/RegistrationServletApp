package com.example.registrationservlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registrationservlet.model.InsertModel
import com.example.registrationservlet.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.app.DatePickerDialog

import android.content.Intent
import android.widget.DatePicker
import java.util.*
import android.widget.ArrayAdapter
import com.example.registrationservlet.model.Role


class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    var day = 0
    var month: Int = 0
    var year: Int = 0

    var roleList = ArrayList<Any>()

    var roleDropdown: ArrayList<Role> = ArrayList<Role>()


//    var roleList: MutableList<Role> = mutableListOf<Role>()


    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        etName.setText("SAKIB")
        etMobile.setText("01933575667")
        etEmail.setText("sssakib@gmail.com")
        etGender.setText("Male")
        etAddress.setText("Dhaka")

        getRoleData()

        btnDatepick.setOnClickListener {

            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this@MainActivity, this@MainActivity, year, month, day)
            datePickerDialog.show()
        }


        //access the spinner

        if (roleList != null) {
            //roleDropdown.clear()
            val i: Iterator<Any> = roleList.iterator()
            while (i.hasNext()) {
                val roleModel = Role(i.next().toString(), i.next().toString())
                roleDropdown.add(roleModel)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roleDropdown)
            roleSpinner.adapter = adapter

        }

        //        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList)
//        roleSpinner.adapter = adapter
//
//        roleSpinner.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                TvRoleM.text = roleList[position].toString()
//
//                Log.d("SSS", "onItemSelected: " + roleList[position])
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//
//            }
//        }


        btnRegister.setOnClickListener {
            doInsert()
        }

        observeViewModel()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {


        etDob.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year)
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


                } else {
                    val i = Intent(this, DetailActivity::class.java)
                    i.putExtra("name", it.name)
                    i.putExtra("mobile", it.mobile)
                    i.putExtra("email", it.email)
                    i.putExtra("age", it.age)
                    i.putExtra("gender", it.gender)
                    i.putExtra("address", it.address)
                    i.putExtra("role", it.role)
                    startActivity(i)
                }


                //Log.e("Login-->", "Error Found")

            }
        })

        userViewModel.roleGet_response_error.observe(this, androidx.lifecycle.Observer {
            it?.let {


                Log.e("Login-->", "Error Found")

            }
        })

        userViewModel.roleGet_response.observe(this, androidx.lifecycle.Observer {
            it?.let {


                if (it.size >= 1) {

                    for (i in it.indices) {
                        val desc = it[i].desc
                        val code = it[i].code

                        roleList.add(desc!!)
                        roleList.add(code!!)
                    }


                } else {
                    Log.e("Login-->", "Error Found")
                }


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
        model.role = TvRoleM.getText().toString().trim()


        this.let { it1 -> userViewModel.doInsert(model) }
    }

    private fun getRoleData() {
        var model = InsertModel()

        model.requestCode = ("2")

        this.let { it1 -> userViewModel.doGetAllRoleList(model) }
    }


}