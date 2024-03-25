package se.magictechnology.pia12androidprojekt.tab3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel
import se.magictechnology.pia12androidprojekt.UserViewmodel

@Composable
fun Profile(shopvm : ShoppingViewmodel, uservm : UserViewmodel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text("Profile")

        Button(onClick = {
            uservm.logout()
        }) {
            Text("logout")
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    var shopvm : ShoppingViewmodel = viewModel()
    shopvm.isPreview = true

    var uservm : UserViewmodel = viewModel()

    Profile(shopvm, uservm)
}