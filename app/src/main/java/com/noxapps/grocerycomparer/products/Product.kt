package com.noxapps.grocerycomparer.products

class Product(
    val name:String,
    val price:String,
    val pricePer:String?=price+" per each",
    val imgSrc:String,
    val sk:Long?=-1,
    val origin:String
) {
}