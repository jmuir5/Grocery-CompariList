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
    Column() {
        Text(text = "home page")
        Button(onClick = { navController.navigate(Routes.Search.Path+"/any")/*TODO: add Comparison*/ }) {
            Text(text = "Add Product")
        }
        //comparison head
        LazyColumn{
            item(){ comparisonCard(comparison = OBComparison(name = "test comparison",
                colesProductId =(482368).toLong(),
                woolworthsProductId = (518128).toLong(),
                igaProductId =(532655).toLong(),
                aldiProductId = (531547).toLong()),navController)}
        }
        //        currentList.forEach(){
        //            item(){ productCard(it)}
        //        }
        //    }

    }
}

@Composable
fun comparisonCard(comparison: OBComparison, navController:NavHostController){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val productBox = ObjectBox.store.boxFor(OBProduct::class.java)
    Row(modifier = Modifier
        .width(screenWidth.dp)
        ) {
        Column(modifier = Modifier.width((screenWidth*0.16).dp), horizontalAlignment = Alignment.CenterHorizontally){//head
            Text(comparison.name)
            Button(onClick = { navController.navigate(Routes.ComparisonView.Path+"/"+comparison.id) }) {
                Text("Edit")
            }
        }
        Column(modifier = Modifier
            .width((screenWidth * 0.21).dp)
            .background(Color(0xFFFFC2C2))
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
            .background(Color(0xFFe8e8e8))
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
            modifier =Modifier.height((width*0.95).dp).width((width*0.95).dp) )
        Text(" ")
        Text("\$NaN ")
        Text(" per ")

    }
}