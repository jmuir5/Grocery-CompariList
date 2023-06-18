package com.noxapps.grocerycomparer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBProduct

@Composable
fun Home(viewModel: HomeViewModel = HomeViewModel(), navController: NavHostController) {
    //var currentList by remember(mutableStateOf())
    val comparisonBox = ObjectBox.store.boxFor(OBComparison::class.java)

    /*comparisonBox.all.forEach{
        if(it.colesProductId+it.woolworthsProductId+
            it.aldiProductId+it.igaProductId==0.toLong()
            &&it.name=="") {
            comparisonBox.remove(it.id)
        }
    }*/
    Column() {
        Text(text = "home page")
        Button(onClick = {
            val comparison = OBComparison()
            comparisonBox.put(comparison)
            navController.navigate(Routes.ComparisonView.Path+"/"+comparison.id)/*TODO: add Comparison*/ }) {
            Text(text = "Add Product")
        }
        //comparison head
        LazyColumn {
            comparisonBox.all.forEach {
                item() {
                    Spacer(modifier = Modifier.height(1.dp))
                    comparisonCard(it, navController)
                }
            }
        }
    }
}

@Composable
fun comparisonCard(comparison: OBComparison, navController:NavHostController){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val productBox = ObjectBox.store.boxFor(OBProduct::class.java)
    Row(modifier = Modifier
        .width(screenWidth.dp).height(200.dp)
        ) {
        Column(modifier = Modifier.width((screenWidth*0.16).dp).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally){//head
            Text(comparison.name)
            Button(onClick = { navController.navigate(Routes.ComparisonView.Path+"/"+comparison.id) }) {
                Text("Edit")
            }
        }
        Column(modifier = Modifier
            .width((screenWidth * 0.21).dp)
            .background(Color(0xFFFFC2C2))
            .fillMaxHeight()
            ){//coles
            if(comparison.colesProductId!= (0).toLong()){
                comparisonItem(product = productBox[comparison.colesProductId], width =screenWidth * 0.21 )
            }
            else{
                emptyComparisonItem(width = screenWidth * 0.21, origin = "coles")
            }


        }
        Column(modifier = Modifier
            .width((screenWidth * 0.21).dp)
            .background(Color(0xFFc4ffc2))
            .fillMaxHeight()
            ){//woolworths
            if(comparison.woolworthsProductId!= (0).toLong()){
                comparisonItem(product = productBox[comparison.woolworthsProductId], width =screenWidth * 0.21 )
            }
            else{
                emptyComparisonItem(width = screenWidth * 0.21, origin = "woolworths")
            }
        }
        Column(modifier = Modifier
            .width((screenWidth * 0.21).dp)
            .background(Color(0xfffffcc2))
            .fillMaxHeight()
            ){//aldi
            if(comparison.aldiProductId!= (0).toLong()){
                comparisonItem(product = productBox[comparison.aldiProductId], width =screenWidth * 0.21 )
            }
            else{
                emptyComparisonItem(width = screenWidth * 0.21, origin = "aldi")
            }
        }
        Column(modifier = Modifier
            .width((screenWidth * 0.21).dp)
            .background(Color(0xFFc7fdff))
            .fillMaxHeight()
            ){//iga
            if(comparison.igaProductId!= (0).toLong()){
                comparisonItem(product = productBox[comparison.igaProductId], width =screenWidth * 0.21 )
            }
            else{
                emptyComparisonItem(width = screenWidth * 0.21, origin = "iga")
            }
        }

    }
}

@Composable
fun comparisonItem(product:OBProduct, width:Double){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = product.imgSrc, contentDescription = null,
            modifier = Modifier
                .height((width * 0.95).dp)
                .width((width * 0.95).dp))
        Text(product.name.substring(0,15))
        Text(product.price)
        product.pricePer?.let { Text(it) }
        
    }

}

@Composable
fun emptyComparisonItem(width:Double, origin:String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.image_blank), contentDescription = null,
            modifier = Modifier
                .height((width * 0.95).dp)
                .width((width * 0.95).dp) )
        Text(" ")
        Text("\$NaN ")
        Text(" per ")

    }
}