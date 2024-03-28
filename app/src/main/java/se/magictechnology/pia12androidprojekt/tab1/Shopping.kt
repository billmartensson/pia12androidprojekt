package se.magictechnology.pia12androidprojekt.tab1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import se.magictechnology.pia12androidprojekt.ShoppingViewmodel
import se.magictechnology.pia12androidprojekt.models.ShopItem
import se.magictechnology.pia12androidprojekt.models.ShopList

@Composable
fun Shopping(shopvm : ShoppingViewmodel, goItemdetail : () -> Unit) {

    val currentshoplist by shopvm.currentshoplist.collectAsState()
    val suggestedfav by shopvm.suggestedfav.collectAsState()

    var addTitle by remember { mutableStateOf("") }
    var addAmount by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text("This is list ${currentshoplist!!.title}")

        Row {

            TextField(value = addTitle, onValueChange = {
                addTitle = it
                shopvm.suggestfav(it)
            }, modifier = Modifier.width(120.dp))

            TextField(value = addAmount, onValueChange = { addAmount = it}, modifier = Modifier.width(70.dp))

            Button(onClick = {
                shopvm.addShopItem(addTitle, addAmount)
            }) {
                Text("Add")
            }
        }

        if(suggestedfav != null) {
            LazyColumn {
                items(suggestedfav!!) { fav ->
                    Row(modifier = Modifier.background(Color.LightGray).clickable {
                        shopvm.addShopItem(fav.title, "1")
                        addTitle = ""
                        shopvm.suggestfav("")
                    }) {
                        Text(fav.title)
                    }
                }
            }
        }

        if(currentshoplist!!.shopitems != null) {
            LazyColumn {
                items(currentshoplist!!.shopitems!!) { shopitem ->
                    Row {
                        Text("${shopitem.title} ${shopitem.amount}")
                    }
                }
            }
        } else {
            Text("No items yet")
        }

    }
}


@Preview
@Composable
fun ShoppingPreview() {

    var shopvm : ShoppingViewmodel = viewModel()
    shopvm.setupPreview()

    Shopping(shopvm, goItemdetail = {})
}