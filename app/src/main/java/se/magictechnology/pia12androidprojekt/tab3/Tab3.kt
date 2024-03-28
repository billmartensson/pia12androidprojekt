package se.magictechnology.pia12androidprojekt.tab3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.magictechnology.pia12androidprojekt.NavigationItem
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel
import se.magictechnology.pia12androidprojekt.UserViewmodel

@Composable
fun Tab3(navController: NavController, shopvm : ShoppingViewmodel) {
    var navhostcon = rememberNavController()

    var uservm : UserViewmodel = viewModel()

    val isloggedin by uservm.isloggedin.collectAsState()

    LaunchedEffect(true) {
        uservm.checkLogin()
    }

    NavHost(navController = navhostcon, startDestination = NavigationItem.Profile.route ) {
        composable(NavigationItem.Profile.route) {
            if(isloggedin) {
                Profile(shopvm, uservm)
            } else {
                Login(shopvm, uservm)
            }

        }
    }
}

@Preview
@Composable
fun Tab3preview() {
    val navController = rememberNavController()
    var shopvm : ShoppingViewmodel = viewModel()

    shopvm.isPreview = true

    Tab3(navController = navController, shopvm = shopvm)
}