package se.magictechnology.pia12androidprojekt.tab1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
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
                navhostcon.navigate("${NavigationItem.Shopping.route}/${it.fbid}")
            }
            )
        }
        composable("${NavigationItem.Shopping.route}/{listid}") {
            val listid = it.arguments?.getString("listid")
            shopvm.setCurrentList(listid!!)

            Shopping(shopvm, goItemdetail = {
                navhostcon.navigate(NavigationItem.ShopitemDetail.route)
            })
        }
        composable(NavigationItem.ShopitemDetail.route) {
            ShopItemDetail()
        }
    }

}

@Preview
@Composable
fun Tab1Preview() {
    val navController = rememberNavController()
    var shopvm : ShoppingViewmodel = viewModel()

    shopvm.isPreview = true

    Tab1(navController = navController, shopvm = shopvm)
}



