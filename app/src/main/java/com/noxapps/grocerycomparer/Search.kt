package com.noxapps.grocerycomparer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBProduct

@Composable
fun Search(viewModel:SearchViewModel= SearchViewModel(), navController: NavHostController
           ,origin:String = "any", comparisonId:Long){
    val searchResult = remember{mutableStateListOf<OBProduct>()}
    var searchText by remember {mutableStateOf("")}
    var holder = 0.toLong()
    Column() {
        Text(text = "search page")
        TextField(value = searchText, onValueChange = {searchText = it})

        Button(onClick = {
            searchResult.removeAll(searchResult)
            viewModel.searchString(searchText, origin).forEach(){
                searchResult.add(it)
            }
        }) {
            Text(text = "search for product name")
        }
        //comparison head
        LazyColumn{
            searchResult.forEach(){
                item(){ productCard(it, comparisonId, navController)}
            }
        }

    }

}


@Composable
fun productCard(product:OBProduct, comparisonId: Long, navController: NavHostController){
    val comparisonBox = ObjectBox.store.boxFor(OBComparison::class.java)
    val activeComparison = comparisonBox[comparisonId]
    Row() {
        AsyncImage(model = product.imgSrc, contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )
        Column() {
            Text(product.name)
            Text(product.price)
            Text(product.origin)
            Text(product.Id.toString())
            Button(onClick = {
                when(product.origin){
                    "coles"->activeComparison.colesProductId = product.Id
                    "woolworths"->activeComparison.woolworthsProductId = product.Id
                    "aldi"->activeComparison.aldiProductId = product.Id
                    "iga"->activeComparison.igaProductId = product.Id
                }
                comparisonBox.put(activeComparison)
                navController.popBackStack()
            }) {
                Text(text = "Select Product")
            }
        }

    }
}