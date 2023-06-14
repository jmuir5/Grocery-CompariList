package com.noxapps.grocerycomparer.classes

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
    constructor(product:Product) : this(
        0,
        product.name,
        product.price,
        product.pricePer,
        product.imgSrc,
        product.sku,
        product.origin
    )
    constructor(id:Long, product:Product):this(
        id,
        product.name,
        product.price,
        product.pricePer,
        product.imgSrc,
        product.sku,
        product.origin
    )
}