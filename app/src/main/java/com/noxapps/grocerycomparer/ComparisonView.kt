package com.noxapps.grocerycomparer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Button

import androidx.navigation.NavHostController
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBProduct
import io.objectbox.annotation.Id


@Composable
fun ComparisonView(viewModel: HomeViewModel = HomeViewModel(), navController: NavHostController, id:Long) {
    val comparisonBox = ObjectBox.store.boxFor(OBComparison::class.java)
    val productBox = ObjectBox.store.boxFor(OBProduct::class.java)
    val comparison = comparisonBox[id]

    Log.d("comparisonTag", comparison.id.toString())

    var comparisonName by remember{ mutableStateOf(comparison.name) }
    Scaffold(
        //topBar ={},
        floatingActionButton = {},
        //drawerContent = {},
        content = {padding->
            Column() {
                Text(modifier = Modifier.padding(padding), text = "ComparisonPage")
                TextField(value = comparisonName, onValueChange = {comparisonName=it},
                    label = { Text(text = "Comparison Name")}
                )
                LazyColumn() {
                    if (comparison.colesProductId == 0.toLong()) {
                        item(){emptyComparisonProductCard(navController, "coles", comparison.id)}
                    } else {
                        item(){comparisonProductCard(
                            product = productBox[comparison.colesProductId],
                            navController = navController,
                            comparison.id
                        )}
                    }
                    if (comparison.woolworthsProductId == 0.toLong()) {
                        item(){emptyComparisonProductCard(navController, "woolworths",comparison.id)}
                    } else {
                        item(){comparisonProductCard(
                            product = productBox[comparison.woolworthsProductId],
                            navController = navController,
                            comparison.id
                        )}
                    }
                    if (comparison.aldiProductId == 0.toLong()) {
                        item(){emptyComparisonProductCard(navController, "aldi",comparison.id)}
                    } else {
                        item(){comparisonProductCard(
                            product = productBox[comparison.aldiProductId],
                            navController = navController,
                            comparison.id
                        )}
                    }
                    if (comparison.igaProductId == 0.toLong()) {
                        item(){emptyComparisonProductCard(navController, "iga",comparison.id)}
                    } else {
                        item(){comparisonProductCard(
                            product = productBox[comparison.igaProductId],
                            navController = navController,
                            comparison.id
                        )}
                    }
                }
            }

        },
        bottomBar = {
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
                onClick = {
                    if(comparison.colesProductId+comparison.woolworthsProductId+
                        comparison.aldiProductId+comparison.igaProductId==0.toLong()
                        &&comparison.name==""){
                        comparisonBox.remove(comparison.id)
                    }else{
                        comparisonBox.put(comparison)
                    }
                    navController.popBackStack()
                }) {
                Text(text = "Save Comparison")
            }


        }
    )
}

@Composable
fun comparisonProductCard(product:OBProduct, navController: NavHostController, compId: Long){
    var expanded by remember { mutableStateOf(false) }
    var colour = when(product.origin){
        "coles"-> Color(0xFFFFC2C2)
        "woolworths"-> Color(0xFFc4ffc2)
        "aldi"-> Color(0xfffffcc2)
        "iga"->Color(0xFFc7fdff)
        else->Color(0xFFFFFFFF)
    }

    if(!expanded){
        Row(modifier= Modifier
            .fillMaxWidth()
            .background(colour)
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
        Column(modifier = Modifier
            .padding(4.dp)
            .background(colour)
            .clickable { expanded = false }, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = product.imgSrc, contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(product.name)
            Text(product.price)
            Text(product.origin)
            Text(product.Id.toString())
            Button(onClick = { navController.navigate(Routes.Search.Path+"/"+product.origin+"/"+compId.toString()) }) {
                Text(text = "Change Product")
            }
        }

    }

}

@Composable
fun emptyComparisonProductCard(navController: NavHostController, origin:String, compId: Long){
    var colour = Color(0xFFe8e8e8)

    Row(modifier= Modifier
        .fillMaxWidth()
        .background(colour)) {
        Image(painter = painterResource(id = R.drawable.image_blank), contentDescription = null,
            modifier = Modifier
                .height((100).dp)
                .width((100).dp) )
        Button(onClick = {
            navController.navigate(Routes.Search.Path+"/"+origin+"/"+compId.toString()) }) {
            Text(text = "Add $origin Product")
        }

    }

}