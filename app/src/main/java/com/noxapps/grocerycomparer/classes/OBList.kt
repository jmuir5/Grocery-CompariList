package com.noxapps.grocerycomparer.classes

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class OBList (
    @Id
    var id:Long = 0,
    var comparisonIds:LongArray = LongArray(0)
        ){
}