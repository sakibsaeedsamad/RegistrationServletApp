package com.example.registrationservlet.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrationservlet.model.Gender
import com.example.registrationservlet.model.InsertModel
import com.example.registrationservlet.model.Role
import com.example.registrationservlet.model.User
import com.example.registrationservlet.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserViewModel : ViewModel() {

    private val apiService = RetrofitClient()
    private val disposable = CompositeDisposable()

    var userInsert_response = MutableLiveData<User>();
    var userInsert_response_error = MutableLiveData<Boolean>();

    var roleGet_response = MutableLiveData<List<Role>>();
    var roleGet_response_error = MutableLiveData<Boolean>();

    var genderGet_response = MutableLiveData<List<Gender>>();
    var genderGet_response_error = MutableLiveData<Boolean>();


    var userListGet_response = MutableLiveData<List<User>>();
    var userListGet_response_error = MutableLiveData<Boolean>();

    fun doInsert(model: InsertModel) {

        disposable.add(
            apiService.doInsert(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<User>() {
                    override fun onSuccess(model: User) {
                        model?.let {
                            Log.d("Success ", "onSuccess: " + model.errorMessage)
                            userInsert_response.value = model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        userInsert_response_error.value = true
                        Log.e("Insert-->", e.toString())

                    }

                })
        )
    }

    fun doGetAllRoleList(model: InsertModel) {

        disposable.add(
            apiService.doGetAllRoleList(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Role>>() {
                    override fun onSuccess(model: List<Role>) {
                        model?.let {
                            roleGet_response.value = model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        roleGet_response_error.value = true
                        Log.e("Role-->", e.toString())

                    }

                })
        )
    }

    fun doGetGenderList(model: InsertModel) {

        disposable.add(
            apiService.doGetGenderList(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Gender>>() {
                    override fun onSuccess(model: List<Gender>) {
                        model?.let {
                            genderGet_response.value = model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        genderGet_response_error.value = true
                        Log.e("Gender-->", e.toString())

                    }

                })
        )
    }


    fun doGetUserList(model: InsertModel) {

        disposable.add(
            apiService.doGetUserList(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<User>>() {
                    override fun onSuccess(model: List<User>) {
                        model?.let {
                            userListGet_response.value = model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        userListGet_response_error.value = true
                        Log.e("Gender-->", e.toString())

                    }

                })
        )
    }


}