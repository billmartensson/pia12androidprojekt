package se.magictechnology.pia12androidprojekt

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShoppingViewmodel : ViewModel() {

    private val _shoptext = MutableStateFlow<String>("START")
    val shoptext : StateFlow<String> get() = _shoptext

    fun changetext(newtext : String) {
        _shoptext.value = newtext
    }

}