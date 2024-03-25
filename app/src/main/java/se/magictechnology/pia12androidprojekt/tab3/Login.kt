package se.magictechnology.pia12androidprojekt.tab3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel
import se.magictechnology.pia12androidprojekt.UserViewmodel

@Composable
fun Login(shopvm : ShoppingViewmodel, uservm : UserViewmodel) {

    var loginemail by remember { mutableStateOf("") }
    var loginpassword by remember { mutableStateOf("") }

    var errormessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text("Login")

        if(errormessage != null) {
            Text(errormessage!!)
        }

        TextField(value = loginemail, onValueChange = { loginemail = it })
        TextField(value = loginpassword, onValueChange = { loginpassword = it })

        Button(onClick = {
            uservm.loginUser(loginemail, loginpassword) {
                errormessage = it
            }
        }) {
            Text("Login")
        }

        Button(onClick = {
            uservm.registerUser(loginemail, loginpassword) {
                errormessage = it
            }
        }) {
            Text("Register")
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    var shopvm : ShoppingViewmodel = viewModel()
    shopvm.isPreview = true

    var uservm : UserViewmodel = viewModel()

    Login(shopvm, uservm)
}