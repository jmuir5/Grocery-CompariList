package com.noxapps.grocerycomparer.classes

class Product(
    val name:String,
    val price:String,
    val pricePer:String?=price+" per each",
    val imgSrc:String,
    val sku:Long=-1,
    val origin:String
) {
}