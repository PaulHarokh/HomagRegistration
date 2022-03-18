package by.paulharokh.testhomag.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.paulharokh.testhomag.remote.Reg
import by.paulharokh.testhomag.remote.RegAnswer

class RegViewModel:ViewModel() {
    var regData = MutableLiveData<Reg>()
    var regResponse = MutableLiveData<RegAnswer>()

}