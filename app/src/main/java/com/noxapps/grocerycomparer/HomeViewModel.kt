package com.noxapps.grocerycomparer

import android.util.Log
import androidx.lifecycle.ViewModel
import com.noxapps.grocerycomparer.classes.OBProduct
import com.noxapps.grocerycomparer.classes.OBProduct_
import io.objectbox.query.QueryBuilder

class HomeViewModel():ViewModel() {
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

