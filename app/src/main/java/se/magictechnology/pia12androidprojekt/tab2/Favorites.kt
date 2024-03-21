package se.magictechnology.pia12androidprojekt.tab2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel

@Composable
fun Favorites(shopvm: ShoppingViewmodel, goFavAdd : () -> Unit) {

    val shoptext by shopvm.shoptext.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("FAVORITES")
        Text(shoptext)

        Button(onClick = {
            shopvm.changetext("CHANGED IN FAV")
        }) {
            Text("CHANGE THE TEXT")
        }


        Button(onClick = { goFavAdd() }) {
            Text("GO ADD")
        }
    }
}