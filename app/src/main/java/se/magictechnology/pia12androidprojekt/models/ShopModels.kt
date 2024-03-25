package se.magictechnology.pia12androidprojekt.models

data class ShopList(val fbid : String, val title : String, var shopitems : List<ShopItem>)


data class ShopItem(val fbid : String, val title : String, val amount : Int, val isBought : Boolean)


