package se.magictechnology.pia12androidprojekt

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewmodel : ViewModel() {

    private val _isloggedin = MutableStateFlow<Boolean>(false)
    val isloggedin : StateFlow<Boolean> get() = _isloggedin

    fun checkLogin() {
        if(Firebase.auth.currentUser!!.isAnonymous) {
            _isloggedin.value = false
        } else {
            _isloggedin.value = true
        }
    }


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

        Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            checkLogin()
        }.addOnFailureListener {

        }
    }

    fun registerUser(email : String, password : String, errorresult : (String?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

        Firebase.auth.currentUser!!.linkWithCredential(credential).addOnSuccessListener {
            checkLogin()
        }.addOnFailureListener {
            //TODO: Visa fel
        }

    }

    fun logout() {
        //_isloggedin.value = false

    }

    fun forgotPassword(email : String) {

    }
    fun changePassword(newpass : String) {

    }
    fun deleteAccount() {

    }



}