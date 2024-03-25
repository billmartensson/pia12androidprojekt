package se.magictechnology.pia12androidprojekt

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewmodel : ViewModel() {

    private val _isloggedin = MutableStateFlow<Boolean>(false)
    val isloggedin : StateFlow<Boolean> get() = _isloggedin




    fun loginUser(email : String, password : String, errorresult : (String?) -> Unit) {

        if(email == "a") {
            errorresult("Dålig epost!!")
            return
        }
        if(password == "") {
            errorresult("Inget lösenord!")
            return
        }
        if(password.length < 5) {
            errorresult("Kort lösenord!")
            return
        }

        _isloggedin.value = true
    }

    fun registerUser(email : String, password : String, errorresult : (String?) -> Unit) {
        _isloggedin.value = true
    }

    fun logout() {
        _isloggedin.value = false
    }

    fun forgotPassword(email : String) {

    }
    fun changePassword(newpass : String) {

    }
    fun deleteAccount() {

    }



}