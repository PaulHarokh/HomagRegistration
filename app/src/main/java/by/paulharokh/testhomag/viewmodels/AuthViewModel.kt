package by.paulharokh.testhomag.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.paulharokh.testhomag.remote.Auth
import by.paulharokh.testhomag.remote.AuthAnswer

class AuthViewModel:ViewModel() {
    var authData = MutableLiveData<Auth>()
    var authResponse = MutableLiveData<AuthAnswer>()
}