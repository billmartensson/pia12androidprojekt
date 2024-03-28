package se.magictechnology.pia12androidprojekt

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import se.magictechnology.pia12androidprojekt.models.ShopFav
import se.magictechnology.pia12androidprojekt.models.ShopItem
import se.magictechnology.pia12androidprojekt.models.ShopList

class ShoppingViewmodel : ViewModel() {

    private val _shoptext = MutableStateFlow<String>("START")
    val shoptext : StateFlow<String> get() = _shoptext

    private val _shoppinglists = MutableStateFlow<List<ShopList>?>(null)
    val shoppinglists : StateFlow<List<ShopList>?> get() = _shoppinglists



    private val _currentshoplist = MutableStateFlow<ShopList?>(null)
    val currentshoplist : StateFlow<ShopList?> get() = _currentshoplist


    private val _favorites = MutableStateFlow<List<ShopFav>?>(null)
    val favorites : StateFlow<List<ShopFav>?> get() = _favorites

    private val _suggestedfav = MutableStateFlow<List<ShopFav>?>(null)
    val suggestedfav : StateFlow<List<ShopFav>?> get() = _suggestedfav


    var isPreview = false

    fun setupPreview() {
        isPreview = true

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
        _currentshoplist.value = l1

        var f1 = ShopFav("f1", "Apelsin")
        var f2 = ShopFav("f2", "Banan")
        var f3 = ShopFav("f3", "Citron")

        var tempFavs = mutableListOf<ShopFav>()
        tempFavs.add(f1)
        tempFavs.add(f2)
        tempFavs.add(f3)

        _favorites.value = tempFavs
    }

    fun logout() {
        var auth = Firebase.auth
        auth.signOut()
        checklogin()
    }
    fun checklogin() {

        var auth = Firebase.auth

        if(auth.currentUser != null) {
            loadfavorites()
            loadshopping()
        } else {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    loadfavorites()
                    loadshopping()
                } else {
                    // TODO: Login fail.
                }
            }
        }


    }


    fun suggestfav(searchtext : String) {
        if(searchtext == "") {
            _suggestedfav.value = mutableListOf<ShopFav>()
            return
        }

        var favs = _favorites.value!!
        var result = favs.filter { it.title.lowercase().contains(searchtext.lowercase()) }

        _suggestedfav.value = result
    }

    fun loadfavorites() {
        if(isPreview) {
            return
        }

        val userid = Firebase.auth.currentUser!!.uid

        val database = Firebase.database
        val myRef = database.getReference("androidshopping")
            .child(userid)
            .child("favorites")

        myRef.get().addOnSuccessListener {
            var tempFavs = mutableListOf<ShopFav>()

            for(childsnap in it.children) {
                var tempFav = childsnap.getValue<ShopFav>()
                tempFav!!.fbid = childsnap.key!!
                tempFavs.add(tempFav)
            }
            _favorites.value = tempFavs

        }.addOnFailureListener {

        }

        /*
        var tempfav = mutableListOf<String>()
        tempfav.add("Mjölk")
        tempfav.add("Bacon")
        tempfav.add("Stol")

        _favorites.value = tempfav
        */
    }

    fun addFavorite(addfav : String) {

        val userid = Firebase.auth.currentUser!!.uid

        var newFav = ShopFav("", addfav)

        val database = Firebase.database
        val myRef = database.getReference("androidshopping")
            .child(userid)
            .child("favorites")
            .push()

        myRef.setValue(newFav).addOnSuccessListener {
            loadfavorites()
        }.addOnFailureListener {

        }

        /*
        var oldfav = _favorites.value!!.toMutableList()
        oldfav.add(addfav)
        _favorites.value = oldfav

         */
    }

    fun deleteFavorite(fav : ShopFav) {
        val userid = Firebase.auth.currentUser!!.uid

        val database = Firebase.database
        val myRef = database.getReference("androidshopping")
            .child(userid)
            .child("favorites")
            .child(fav.fbid)
            .removeValue()
            .addOnSuccessListener {
                loadfavorites()
            }
            .addOnFailureListener {
                //TODO: Visa fel
            }
    }

    fun addShoppingList(listtitle : String) {

        val userid = Firebase.auth.currentUser!!.uid

        var newList = ShopList("", listtitle, mutableListOf<ShopItem>())

        val database = Firebase.database
        val myRef = database.getReference("androidshopping")
            .child(userid)
            .child("lists")
            .push()


        myRef.setValue(newList).addOnSuccessListener {
            loadshopping()
        }.addOnFailureListener {

        }
    }

    fun loadshopping() {


        val userid = Firebase.auth.currentUser!!.uid

        val database = Firebase.database
        val myRef = database.getReference("androidshopping")
            .child(userid)
            .child("lists")

        myRef.get().addOnSuccessListener {
            var tempLists = mutableListOf<ShopList>()


            for(childsnap in it.children) {
                Log.i("pia12debug", childsnap.value.toString())
                var templist = ShopList()
                templist.title = childsnap.child("title").getValue<String>()!!
                templist.fbid = childsnap.key!!

                var tempItems = mutableListOf<ShopItem>()

                for(itemsnap in childsnap.child("shopitems").children) {
                    var tempItem = itemsnap.getValue<ShopItem>()
                    tempItems.add(tempItem!!)
                }

                templist.shopitems = tempItems


                tempLists.add(templist)
            }

            if(_currentshoplist.value != null) {
                _currentshoplist.value = tempLists.find { it.fbid == _currentshoplist.value!!.fbid }
            }

            _shoppinglists.value = tempLists

        }.addOnFailureListener {
            //TODO: Visa fel
        }

    }

    fun setCurrentList(listid : String) {
        _currentshoplist.value = getShoppinglistForId(listid)
    }

    fun addShopItem(itemtitle : String, itemamountstring : String) {

        var itemamount = itemamountstring.toIntOrNull()

        if(itemamount == null) {
            // TODO: Visa fel
            return
        }

        var newShopItem = ShopItem("", itemtitle, itemamount, false)

        val userid = Firebase.auth.currentUser!!.uid

        val database = Firebase.database
        val myRef = database.getReference("androidshopping")
            .child(userid)
            .child("lists")
            .child(_currentshoplist.value!!.fbid)
            .child("shopitems")
            .push()

        myRef.setValue(newShopItem).addOnSuccessListener {
            loadshopping()
        }.addOnFailureListener {

        }

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