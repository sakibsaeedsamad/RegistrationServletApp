package com.example.registrationservlet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationservlet.R
import com.example.registrationservlet.model.User
import kotlinx.android.synthetic.main.user_list_card.view.*


class UserListAdapter (val userList: ArrayList<User>, val listener: OnRowClickListener): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    fun setUserList(newUserList : List<User>){
        userList.clear()
        userList.addAll(newUserList)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter.UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.user_list_card,parent,false)

        return UserListViewHolder(inflater,listener)
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserListViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            listener.onUserClick(userList[position])
        }
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserListViewHolder(view: View, val listener: OnRowClickListener): RecyclerView.ViewHolder(view) {

        val name = view.tv_name
        val email = view.tv_email
        val address = view.tv_address
        val mobile = view.tv_mobile
        val roleDesc = view.tv_role
        val age = view.tv_age
        val gender = view.tv_gender
        val roleCode = view.tv_roleCode


        fun bind(data : User){

            name.text = data.name
            email.text = data.email
            address.text = data.address
            mobile.text = data.mobile
            roleDesc.text = data.roleDesc
            age.text = data.age
            gender.text = data.gender
            roleCode.text = data.role
        }

    }

    interface OnRowClickListener {
        fun onUserClick(user: User)
    }
}