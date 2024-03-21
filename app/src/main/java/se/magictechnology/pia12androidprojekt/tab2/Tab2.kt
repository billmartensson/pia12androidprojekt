package se.magictechnology.pia12androidprojekt.tab2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.magictechnology.pia12androidprojekt.NavigationItem
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel

@Composable
fun Tab2(navController: NavController, shopvm : ShoppingViewmodel) {
    var navhostcon = rememberNavController()
    NavHost(navController = navhostcon, startDestination = NavigationItem.Favorites.route ) {
        composable(NavigationItem.Favorites.route) {
            Favorites(shopvm, goFavAdd = {
                navhostcon.navigate(NavigationItem.FavoritesAdd.route)
            })
        }
        composable(NavigationItem.FavoritesAdd.route) {
            FavoritesAdd()
        }
    }
}



