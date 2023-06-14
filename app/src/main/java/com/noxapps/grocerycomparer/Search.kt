package com.noxapps.grocerycomparer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBProduct

@Composable
fun Search(viewModel:SearchViewModel= SearchViewModel()) {
    val searchResult = remember{mutableStateListOf<OBProduct>()}
    var searchText by remember {mutableStateOf("")}
    Column() {
        Text(text = "search page")
        TextField(value = searchText, onValueChange = {searchText = it})

        Button(onClick = {
            searchResult.forEach(){
                searchResult.remove(it)//doesnt work
            }
            viewModel.searchString(searchText).forEach(){
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