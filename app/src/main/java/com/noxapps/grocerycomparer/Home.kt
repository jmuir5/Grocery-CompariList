package com.noxapps.grocerycomparer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBProduct

@Composable
fun Home(viewModel: HomeViewModel = HomeViewModel(), navController: NavHostController) {
    //viewModel.initialiseData(LocalContext.current)
    Text(text = "home page")
    val motherProducts = viewModel.findMother()
    LazyColumn{
        motherProducts.forEach(){
            item(){ productCard(it)}
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
        }

    }
}
