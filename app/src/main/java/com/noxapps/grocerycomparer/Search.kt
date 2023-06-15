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
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBProduct

@Composable
fun Search(viewModel:SearchViewModel= SearchViewModel(),origin:String = "any") {
    val searchResult = remember{mutableStateListOf<OBProduct>()}
    var searchText by remember {mutableStateOf("")}
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
                item(){ productCard(it)}
            }
        }

    }
}


@Composable
fun productCard(product:OBProduct){
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
        }

    }
}