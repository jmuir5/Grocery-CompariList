package com.noxapps.grocerycomparer.products

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class OBProduct(
    @Id
    var Id:Long=0,
    val name:String="",
    val price:String="NaN",
    val pricePer:String?=price+" per each",
    val imgSrc:String="404",
    val sku:Long=-1,
    val origin:String="???"
) {
}