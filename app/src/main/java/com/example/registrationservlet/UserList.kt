package com.example.registrationservlet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationservlet.adapter.UserListAdapter
import com.example.registrationservlet.model.InsertModel
import com.example.registrationservlet.model.Role
import com.example.registrationservlet.model.User
import com.example.registrationservlet.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_user_list.*
import java.util.ArrayList

class UserList : AppCompatActivity(), UserListAdapter.OnRowClickListener {


    private lateinit var userViewModel: UserViewModel
    var roleList = ArrayList<Any>()

    var roleL: ArrayList<Role> = ArrayList<Role>()

    var uList = ArrayList<Any>()

    var userL: ArrayList<User> = ArrayList<User>()


    private val userListAdapter = UserListAdapter(arrayListOf(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        getUserListData()

        observeViewModel()

        addRecyclerView()


    }

    private fun addRecyclerView() {
        recycViewUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter

        }
    }


    private fun observeViewModel() {


        userViewModel.userListGet_response_error.observe(this, androidx.lifecycle.Observer {
            it?.let {


                Log.e("UserList-->", "Error Found")

            }
        })

        userViewModel.userListGet_response.observe(this, androidx.lifecycle.Observer {
            it?.let {


                if (it.size >= 1) {

                    userListAdapter.setUserList(it)


                } else {
                    Log.e("UserList-->", "Error Found")
                }


            }
        })


    }


    private fun getUserListData() {
        var model = InsertModel()

        model.requestCode = ("4")

        this.let { it1 ->
            userViewModel.doGetUserList(model)
        }
    }


    override fun onUserClick(user: User) {

            val i = Intent(this, DetailActivity::class.java)
            i.putExtra("name", user.name)
            i.putExtra("mobile", user.mobile)
            i.putExtra("email", user.email)
            i.putExtra("age", user.age)
            i.putExtra("gender", user.gender)
            i.putExtra("address", user.address)
            i.putExtra("role", user.roleDesc)
            startActivity(i)

    }
}