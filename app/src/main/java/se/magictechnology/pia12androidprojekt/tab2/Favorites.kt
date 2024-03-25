package se.magictechnology.pia12androidprojekt.tab2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel

@Composable
fun Favorites(shopvm: ShoppingViewmodel, goFavAdd : () -> Unit) {

    val favorites by shopvm.favorites.collectAsState()

    var addFav by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        shopvm.loadfavorites()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text("FAVORITES")

        Row {
            TextField(value = addFav, onValueChange = { addFav = it })

            Button(onClick = {
                shopvm.addFavorite(addFav)
            }) {
                Text("Add")
            }
        }

        if(favorites != null) {
            LazyColumn {
                items(favorites!!) { fav ->
                    Row {
                        Text(fav)
                    }
                }
            }
        } else {
            Text("Loading...")
        }


    }
}

@Preview
@Composable
fun FavoritesPreview() {

    var shopvm : ShoppingViewmodel = viewModel()
    shopvm.isPreview = true

    Favorites(shopvm, goFavAdd = {})
}