package com.noxapps.grocerycomparer

import android.util.Log
import com.noxapps.grocerycomparer.products.OBProduct
import com.noxapps.grocerycomparer.products.OBProduct_
import com.noxapps.grocerycomparer.products.Product
import io.objectbox.query.QueryBuilder
import kotlin.system.measureTimeMillis

class HomeViewModel {
    val productBox = ObjectBox.store.boxFor(OBProduct::class.java)







    fun findMother(): MutableList<OBProduct> {
        val productBox = ObjectBox.store.boxFor(OBProduct::class.java)
        val MotherQuery = productBox
            .query().contains(OBProduct_.name, "ibuprofen", QueryBuilder.StringOrder.CASE_INSENSITIVE)
            .order(OBProduct_.origin).order(OBProduct_.name)
            .build()
        val result = MotherQuery.find()
        Log.d("results", result.toString())
        return result


    }
}

