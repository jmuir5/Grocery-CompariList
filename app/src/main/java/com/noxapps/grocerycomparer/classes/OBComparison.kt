package com.noxapps.grocerycomparer.classes

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class OBComparison (
    @Id
    var id:Long = 0,
    var name:String = "",
    var colesProductId:Long = 0,
    var woolworthsProductId:Long = 0,
    var igaProductId:Long = 0,
    var aldiProductId:Long = 0
        ) {
}