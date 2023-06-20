package com.noxapps.grocerycomparer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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
    val focusManager = LocalFocusManager.current
    val productBox = ObjectBox.store.boxFor(OBProduct::class.java)



    Column(modifier = Modifier.padding(4.dp)) {
        TextField(value = searchText,
            label = {Text(text="Search...")},
            onValueChange = {
                searchText = it
                searchResult.removeAll(searchResult)
                if (searchText.length >2) {
                    viewModel.searchString(searchText, origin, productBox).forEach() { it2 ->
                        searchResult.add(it2)

                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (searchText.length <2&&searchText!="") {
                        searchResult.removeAll(searchResult)
                        viewModel.searchString(searchText, origin, productBox).forEach() { it2 ->
                            searchResult.add(it2)
                        }
                    }
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn{
            if(searchResult.isEmpty()) {
                item(){
                    infoPanel(infoArray = viewModel.originIndication(origin))
                }

            }
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

@Composable
fun infoPanel(infoArray:List<Int>){
    val origin = when(infoArray[0]){
        0->"Coles"
        1->"Woolworths"
        2->"Aldi"
        3->"IGA"
        else->"All"
    }
    val categoryArray = listOf("Produce","Meat", "Pantry", "Alcohol", "Tobacco", "Seasonal")
    Column() {
        Text("Currently Searching $origin products")
        categoryArray.forEachIndexed() { index, it ->
            Text("${infoEmoji(infoArray[index + 1])} $it")
        }
    }
}

fun infoEmoji(input:Int):String{
    when(input){
        0->return "❌"
        1->return "✔"
        else -> return "❓"
    }
}