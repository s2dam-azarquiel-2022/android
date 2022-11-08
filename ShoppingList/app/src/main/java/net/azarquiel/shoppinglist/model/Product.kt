package net.azarquiel.shoppinglist.model

data class Product(
    var id: Int = 0,
    var name: String = "",
    var quantity: Int = 0,
    var price: Float = 0F,
    var bought: Boolean = false,
)