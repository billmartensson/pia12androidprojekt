package se.magictechnology.pia12androidprojekt.models

data class ShopList(var fbid : String = "", var title : String = "", var shopitems : List<ShopItem>? = null)

data class ShopItem(val fbid : String = "", val title : String = "", val amount : Int = 0, val isBought : Boolean = false)


data class ShopFav(var fbid : String = "", var title : String = "")