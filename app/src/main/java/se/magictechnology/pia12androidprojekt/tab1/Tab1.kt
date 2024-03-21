package se.magictechnology.pia12androidprojekt.tab1

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.magictechnology.pia12androidprojekt.NavigationItem
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel
import se.magictechnology.pia12androidprojekt.tab2.FavoritesAdd

@Composable
fun Tab1(navController: NavController, shopvm : ShoppingViewmodel) {

    var navhostcon = rememberNavController()



    NavHost(navController = navhostcon, startDestination = NavigationItem.ShoppingLists.route ) {
        composable(NavigationItem.ShoppingLists.route) {
            ShoppingLists(shopvm, goShoplist = {
                navhostcon.navigate(NavigationItem.Shopping.route)
            }
            )
        }
        composable(NavigationItem.Shopping.route) {
            Shopping(goItemdetail = {
                navhostcon.navigate(NavigationItem.ShopitemDetail.route)
            })
        }
        composable(NavigationItem.ShopitemDetail.route) {
            ShopItemDetail()
        }
    }

}





