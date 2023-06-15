package com.noxapps.grocerycomparer

sealed class Routes(val Path:String) {
    object Init:Routes("Init")
    object Home:Routes("Home")
    object Search:Routes("Search")
    object ComparisonView:Routes("ComparisonView")

}