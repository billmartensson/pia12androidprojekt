package se.magictechnology.pia12androidprojekt.tab1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import se.magictechnology.pia12androidprojekt.models.ShopList

@Composable
fun ShoppingLists(shopvm : ShoppingViewmodel, goShoplist : (ShopList) -> Unit) {

    val shoppinglists by shopvm.shoppinglists.collectAsState()

    var addList by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        shopvm.loadshopping()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text("SHOPPING LISTS")

        Row {
            TextField(value = addList, onValueChange = { addList = it })
            Button(onClick = {
                shopvm.addShoppingList(addList)
            }) {
                Text("Add")
            }
        }

        if(shoppinglists != null) {
            LazyColumn {
                items(shoppinglists!!) { shoplist ->
                    Row(modifier = Modifier.clickable {
                        goShoplist(shoplist)
                    }) {
                        Text(shoplist.title)

                        if(shoplist.shopitems != null) {
                            Text(shoplist.shopitems!!.size.toString())
                        }
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
fun ShoppingListsPreview() {
    var shopvm : ShoppingViewmodel = viewModel()
    shopvm.setupPreview()
    ShoppingLists(shopvm, goShoplist = {})
}