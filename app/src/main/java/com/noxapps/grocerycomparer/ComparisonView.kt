package com.noxapps.grocerycomparer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Button

import androidx.navigation.NavHostController
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBProduct


@Composable
fun ComparisonView(viewModel: HomeViewModel = HomeViewModel(), navController: NavHostController, id:Long) {

    val comparisonBox = ObjectBox.store.boxFor(OBComparison::class.java)
    Scaffold(
        //topBar ={},
        floatingActionButton = {},
        //drawerContent = {},
        content = {padding->
            Text(modifier = Modifier.padding(padding),text = "ComparisonPage")
        },
        bottomBar = {
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
                onClick = {
                    //comparisonBox.put(OBProduct(id, colesP, wolliesP,aldiP, igaP))
                    navController.popBackStack()
                }) {
                Text(text = "Save Comparison")
            }


        }
    )
}

@Composable
fun comparisonProductCard(product:OBProduct, navController: NavHostController){
    var expanded by remember { mutableStateOf(false) }


    if(!expanded){
        Row(modifier= Modifier
            .fillMaxWidth()
            .clickable { expanded = true }) {
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
    }else{
        Column(modifier = Modifier.padding(4.dp).clickable { expanded=false }, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = product.imgSrc, contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(product.name)
            Text(product.price)
            Text(product.origin)
            Text(product.Id.toString())
            Button(onClick = { navController.navigate(Routes.Search.Path+"/"+product.origin) }) {
                Text(text = "Change Product")
            }
        }

    }

}