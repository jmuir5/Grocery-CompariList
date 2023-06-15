package com.noxapps.grocerycomparer

import android.util.Log
import androidx.lifecycle.ViewModel
import com.noxapps.grocerycomparer.classes.OBProduct
import com.noxapps.grocerycomparer.classes.OBProduct_
import io.objectbox.query.QueryBuilder

class SearchViewModel():ViewModel() {
    lateinit var origin:String

    fun searchString(searchString:String, origin:String): MutableList<OBProduct> {
        val productBox = ObjectBox.store.boxFor(OBProduct::class.java)
        var Query = productBox
            .query().contains(OBProduct_.name, searchString, QueryBuilder.StringOrder.CASE_INSENSITIVE)
            .order(OBProduct_.origin).order(OBProduct_.name)
            .build()
        if(origin!="any"){
            Query = productBox
                .query(OBProduct_.origin.equal(origin))
                .contains(OBProduct_.name, searchString, QueryBuilder.StringOrder.CASE_INSENSITIVE)

                .order(OBProduct_.origin).order(OBProduct_.name)
                .build()
        }
        val result = Query.find()
        Log.d("results", result.toString())
        return result


    }
}