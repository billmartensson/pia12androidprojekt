package se.magictechnology.pia12androidprojekt.tab3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.magictechnology.pia12androidprojekt.NavigationItem
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel

@Composable
fun Tab3(navController: NavController, shopvm : ShoppingViewmodel) {
    var navhostcon = rememberNavController()
    NavHost(navController = navhostcon, startDestination = NavigationItem.Profile.route ) {
        composable(NavigationItem.Profile.route) {
            Profile()
        }
    }
}

@Composable
fun Profile() {
    Text("Profile")
}