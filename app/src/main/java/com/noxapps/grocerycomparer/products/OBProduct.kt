package com.noxapps.grocerycomparer.products

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class OBProduct(
    @Id
    var Id:Long,
    val name:String,
    val price:String,
    val pricePer:String?=price+" per each",
    val imgSrc:String,
    val sku:Long?=-1,
    val origin:String
) {
}