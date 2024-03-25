package se.magictechnology.pia12androidprojekt

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import se.magictechnology.pia12androidprojekt.models.ShopItem
import se.magictechnology.pia12androidprojekt.models.ShopList

class ShoppingViewmodel : ViewModel() {

    private val _shoptext = MutableStateFlow<String>("START")
    val shoptext : StateFlow<String> get() = _shoptext


    private val _shoppinglists = MutableStateFlow<List<ShopList>?>(null)
    val shoppinglists : StateFlow<List<ShopList>?> get() = _shoppinglists



    private val _currentshoplist = MutableStateFlow<ShopList?>(null)
    val currentshoplist : StateFlow<ShopList?> get() = _currentshoplist


    private val _favorites = MutableStateFlow<List<String>?>(null)
    val favorites : StateFlow<List<String>?> get() = _favorites


    var isPreview = false


    fun loadfavorites() {

        var tempfav = mutableListOf<String>()
        tempfav.add("Mjölk")
        tempfav.add("Bacon")
        tempfav.add("Stol")

        _favorites.value = tempfav

    }

    fun addFavorite(addfav : String) {
        var oldfav = _favorites.value!!.toMutableList()
        oldfav.add(addfav)
        _favorites.value = oldfav
    }

    fun loadshopping() {

        var tempshoplist = mutableListOf<ShopList>()

        var tempItems = mutableListOf<ShopItem>()
        var s1 = ShopItem("a1", "Köp A", 1, false)
        var s2 = ShopItem("a2", "Köp B", 7, true)

        tempItems.add(s1)
        tempItems.add(s2)

        var l1 = ShopList("x1","Ett", tempItems)
        var l2 = ShopList("x2","Två", tempItems)
        var l3 = ShopList("x3","tre", tempItems)

        tempshoplist.add(l1)
        tempshoplist.add(l2)
        tempshoplist.add(l3)

        _shoppinglists.value = tempshoplist
    }

    fun setCurrentList(listid : String) {
        _currentshoplist.value = getShoppinglistForId(listid)
    }

    fun addShopItem(itemtitle : String, itemamount : String) {

        // TODO: Save to firebase

        var newShopItem = ShopItem("add1", itemtitle, 1, false)

        var oldList = _currentshoplist.value

        var oldItems = _currentshoplist.value!!.shopitems.toMutableList()
        oldItems.add(newShopItem)

        var newList = ShopList(oldList!!.fbid, oldList!!.title,oldItems)

        _currentshoplist.value = newList

        loadshopping()

    }

    fun getShoppinglistForId(listid : String) : ShopList {
        val alllists = _shoppinglists.value
        val foundlist = alllists!!.find { it.fbid == listid }
        return foundlist!!
    }


    fun changetext(newtext : String) {
        _shoptext.value = newtext
    }

}