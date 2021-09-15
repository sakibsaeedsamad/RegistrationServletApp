package com.example.registrationservlet

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.registrationservlet.model.Gender
import com.example.registrationservlet.model.InsertModel
import com.example.registrationservlet.model.Role
import com.example.registrationservlet.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, View.OnClickListener {

    var day = 0
    var month: Int = 0
    var year: Int = 0

    var roleList = ArrayList<Any>()
    var genderList = ArrayList<Any>()

    var roleDropdown: ArrayList<Role> = ArrayList<Role>()
    var roleDesc: ArrayList<String> = ArrayList<String>()

    var gendDesc: ArrayList<String> = ArrayList<String>()

    var roleCode: String = ""

    var genderString: String = ""

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)




        etName.setText("SAKIB")
        etMobile.setText("01933575667")
        etEmail.setText("sssakib@gmail.com")
        etAddress.setText("Dhaka")

        getRoleData()

        getGenderData()

        btnDatepick.setOnClickListener {

            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this@MainActivity, this@MainActivity, year, month, day)
            datePickerDialog.show()
        }


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


                Log.e("Insert-->", "Error Found")

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


            }
        })

        userViewModel.roleGet_response_error.observe(this, androidx.lifecycle.Observer {
            it?.let {


                Log.e("RoleList-->", "Error Found")

            }
        })

        userViewModel.roleGet_response.observe(this, androidx.lifecycle.Observer {
            it?.let {


                if (it.size >= 1) {

                    for (i in it.indices) {

                        val code = it[i].code
                        val desc = it[i].desc
                        roleList.add(code!!)
                        roleList.add(desc!!)

                    }

                    addDropDownList()

                } else {
                    Log.e("RoleList-->", "Error Found")
                }


            }
        })


        userViewModel.genderGet_response_error.observe(this, androidx.lifecycle.Observer {
            it?.let {


                Log.e("Gender-->", "Error Found")

            }
        })

        userViewModel.genderGet_response.observe(this, androidx.lifecycle.Observer {
            it?.let {


                if (it.size >= 1) {

                    for (i in it.indices) {

                        val genCode = it[i].genCode
                        val genDesc = it[i].genDesc
                        genderList.add(genCode!!)
                        genderList.add(genDesc!!)

                    }

                    getGenderList()


                } else {
                    Log.e("Gender-->", "Error Found")
                }


            }
        })


    }

    private fun getGenderData() {
        var model = InsertModel()

        model.requestCode = ("3")

        this.let { it1 ->
            userViewModel.doGetGenderList(model)
        }
    }


    private fun doInsert() {
        var model = InsertModel()
        model.requestCode = ("1")
        model.name = etName.getText().toString().trim()
        model.mobile = etMobile.getText().toString().trim()
        model.email = etEmail.getText().toString().trim()
        model.dob = etDob.getText().toString().trim()
        model.gender = genderString
        model.address = etDob.getText().toString().trim()
        model.role = roleCode


        this.let { it1 -> userViewModel.doInsert(model) }
    }

    private fun getRoleData() {
        var model = InsertModel()

        model.requestCode = ("2")

        this.let { it1 -> userViewModel.doGetAllRoleList(model) }
    }

    fun addDropDownList() {

        if (roleList != null) {
            // roleDropdown.clear()
            val i: Iterator<Any> = roleList.iterator()
            while (i.hasNext()) {
                val roleModel = Role(i.next().toString(), i.next().toString())
                roleDropdown.add(roleModel)
                roleDesc.add(roleModel.desc.toString())

            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roleDesc)
            roleSpinner.adapter = adapter

            roleSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    TvRoleM.text = roleDropdown[position].desc
                    roleCode = roleDropdown[position].code.toString()


                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
    }

    fun getGenderList() {


        if (genderList != null) {
           roleDropdown.clear()
            val i: Iterator<Any> = genderList.iterator()
            while (i.hasNext()) {
                val roleModel = Gender(i.next().toString(), i.next().toString())
                gendDesc.add(roleModel.genDesc.toString())

            }
            addRadioButtons(gendDesc.size)

        }
    }

    fun addRadioButtons(number: Int) {
        radioGroup!!.orientation = LinearLayout.VERTICAL

        for (i in 1..number) {
            val rdbtn = RadioButton(this)
            rdbtn.id = View.generateViewId()
            rdbtn.setText(gendDesc[i - 1])
            rdbtn.setOnClickListener(this)
            val params =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
            rdbtn.setLayoutParams(params)
            radioGroup!!.addView(rdbtn)
        }
    }

    override fun onClick(v: View?) {
        Log.d("Radio Button", " Name " + (v as RadioButton).text + " Id is " + v.getId())
        genderString = (v as RadioButton).text.toString()
    }
}