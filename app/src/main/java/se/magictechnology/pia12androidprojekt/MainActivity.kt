package se.magictechnology.pia12androidprojekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.magictechnology.pia12androidprojekt.tab1.Tab1
import se.magictechnology.pia12androidprojekt.tab2.Tab2
import se.magictechnology.pia12androidprojekt.tab3.Tab3
import se.magictechnology.pia12androidprojekt.ui.theme.Pia12androidprojektTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pia12androidprojektTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootScreen()
                }
            }
        }
    }
}


sealed class NavigationItem(var route: String, val iconid: Int, val iconidoff: Int, var title: String) {
    object Tab1 : NavigationItem("tab1", R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground, "Shopping")
    object Tab2 : NavigationItem("tab2", R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground, "Favorites")
    object Tab3 : NavigationItem("tab3", R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground, "Profile")

    object ShoppingLists : NavigationItem("shoppinglists", R.drawable.ic_launcher_background,R.drawable.ic_launcher_background, "X")
    object Shopping : NavigationItem("shopping", R.drawable.ic_launcher_background,R.drawable.ic_launcher_background, "Y")
    object ShopitemDetail : NavigationItem("shopitemdetail", R.drawable.ic_launcher_background,R.drawable.ic_launcher_background, "Z")

    object Favorites : NavigationItem("favorites", R.drawable.ic_launcher_background,R.drawable.ic_launcher_background, "Z")
    object FavoritesAdd : NavigationItem("favoritesadd", R.drawable.ic_launcher_background,R.drawable.ic_launcher_background, "Z")

    object Profile : NavigationItem("profile", R.drawable.ic_launcher_background,R.drawable.ic_launcher_background, "Z")

}

enum class Tab1screens() {
    ShoppingLists,
    Shopping,
    ShopItemDetail
}

enum class Tab2screens() {
    Favorites,
    FavoriteAdd
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    var shopvm : ShoppingViewmodel = viewModel()

    LaunchedEffect(true) {
        shopvm.loadfavorites()
        shopvm.loadshopping()
    }

    val items = listOf(
        NavigationItem.Tab1,
        NavigationItem.Tab2,
        NavigationItem.Tab3,
    )


    Scaffold(
        /*
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
         */
        bottomBar = {
            BottomNavigation(backgroundColor = Color.Red, contentColor = Color.Cyan) {
                items.forEachIndexed { index, screen ->
                    BottomNavigationItem(
                        icon = { Image(painter = painterResource(id = if (selectedItem == index) screen.iconid else screen.iconidoff), "", modifier = Modifier.size(30.dp)) },
                        label = { Text(screen.title, color = if(selectedItem == index) Color.Green else Color.White) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        selectedContentColor = Color.Blue,
                        unselectedContentColor = Color.Red
                    )
                }
            }

        }
        /*
        ,
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.alpha(if(selectedItem == 0) 1f else 0f ), onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
         */
    ) { innerPadding ->
        NavHost(navController, startDestination = NavigationItem.Tab1.route, Modifier.padding(innerPadding)) {
            composable(NavigationItem.Tab1.route) { Tab1(navController, shopvm) }
            composable(NavigationItem.Tab2.route) { Tab2(navController, shopvm) }
            composable(NavigationItem.Tab3.route) { Tab3(navController, shopvm) }
        }
    }
}






