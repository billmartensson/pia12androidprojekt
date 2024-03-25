package se.magictechnology.pia12androidprojekt.tab1

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
import se.magictechnology.pia12androidprojekt.models.ShopItem
import se.magictechnology.pia12androidprojekt.models.ShopList

@Composable
fun Shopping(shopvm : ShoppingViewmodel, goItemdetail : () -> Unit) {

    val currentshoplist by shopvm.currentshoplist.collectAsState()

    var addTitle by remember { mutableStateOf("") }
    var addAmount by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text("This is list ${currentshoplist!!.title}")

        Row {
            TextField(value = addTitle, onValueChange = { addTitle = it})
            TextField(value = addAmount, onValueChange = { addAmount = it})
            Button(onClick = {
                shopvm.addShopItem(addTitle, addAmount)
            }) {
                Text("Add")
            }
        }

        LazyColumn {
            items(currentshoplist!!.shopitems) { shopitem ->
                Row {
                    Text(shopitem.title)
                }
            }
        }

    }
}


@Preview
@Composable
fun ShoppingPreview() {

    var shopvm : ShoppingViewmodel = viewModel()
    shopvm.isPreview = true

    Shopping(shopvm, goItemdetail = {})
}