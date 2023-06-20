package com.noxapps.grocerycomparer

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.noxapps.grocerycomparer.classes.OBComparison
import com.noxapps.grocerycomparer.classes.OBComparison_
import com.noxapps.grocerycomparer.classes.OBProduct
import io.objectbox.android.AndroidScheduler
import io.objectbox.annotation.Id
import io.objectbox.reactive.DataSubscriptionList


@Composable
fun ComparisonView(viewModel: HomeViewModel = HomeViewModel(), navController: NavHostController, id:Long) {
    val comparisonBox = ObjectBox.store.boxFor(OBComparison::class.java)
    val productBox = ObjectBox.store.boxFor(OBProduct::class.java)
    var comparison by remember { mutableStateOf(comparisonBox[id])}
    var comparisonName by remember{ mutableStateOf(comparison.name) }

    val focusManager = LocalFocusManager.current

    if (comparisonName == "123Placeholder321"){
        comparison.name=""
        comparisonName = ""
        comparisonBox.put(comparison)
    }
    var backPressed = remember{ mutableStateOf(false) }
    BackHandler{
        backPressed.value = true
    }
    //Log.d("comparisonTag", comparison.id.toString())


    Scaffold(
        //topBar ={},
        floatingActionButton = {},
        //drawerContent = {},
        content = {padding->
            Column(modifier = Modifier.padding(padding),) {
                TextField(value = comparisonName, onValueChange = {
                    comparisonName=it;comparison.name=it},
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {

                            focusManager.clearFocus()
                        }
                    ),
                    label = { Text(text = "Comparison Name")}
                )

                LazyColumn() {
                    if (comparison.colesProductId == 0.toLong()) {
                        item() {
                            emptyComparisonProductCard(
                                navController,
                                "coles",
                                comparison.id
                            )
                        }
                    } else {
                        item() {
                            comparisonProductCard(
                                product = productBox[comparison.colesProductId],
                                navController = navController,
                                comparison.id
                            )
                        }
                    }
                    if (comparison.woolworthsProductId == 0.toLong()) {
                        item() {
                            emptyComparisonProductCard(
                                navController,
                                "woolworths",
                                comparison.id
                            )
                        }
                    } else {
                        item() {
                            comparisonProductCard(
                                product = productBox[comparison.woolworthsProductId],
                                navController = navController,
                                comparison.id
                            )
                        }
                    }
                    if (comparison.aldiProductId == 0.toLong()) {
                        item() {
                            emptyComparisonProductCard(
                                navController,
                                "aldi",
                                comparison.id
                            )
                        }
                    } else {
                        item() {
                            comparisonProductCard(
                                product = productBox[comparison.aldiProductId],
                                navController = navController,
                                comparison.id
                            )
                        }
                    }
                    if (comparison.igaProductId == 0.toLong()) {
                        item() {
                            emptyComparisonProductCard(
                                navController,
                                "iga",
                                comparison.id
                            )
                        }
                    } else {
                        item() {
                            comparisonProductCard(
                                product = productBox[comparison.igaProductId],
                                navController = navController,
                                comparison.id
                            )
                        }
                    }
                    item() { Spacer(modifier = Modifier.height(80.dp)) }


                }

            }

        },
        bottomBar = {
            Row(modifier = Modifier
                .fillMaxWidth()) {
                Button(modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .height(80.dp).background(Color.Red),
                    onClick = {
                        val holder = comparison.id
                        comparison = OBComparison()
                        comparisonBox.remove(holder)
                        navController.popBackStack()

                    }) {
                    Text(text = "❌")
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                    onClick = {
                        try {
                            if (comparison.colesProductId + comparison.woolworthsProductId +
                                comparison.aldiProductId + comparison.igaProductId == 0.toLong()
                                && comparison.name == ""
                            ) {
                                val holder = comparison.id
                                comparison = OBComparison()
                                comparisonBox.remove(holder)
                            } else {
                                comparisonBox.put(comparison)
                            }
                        } catch (e: Exception) {
                        } finally {
                            navController.popBackStack()
                        }
                    }) {
                    Text(text = "Save Comparison")
                }
            }


        }
    )
    if(backPressed.value) {
        AlertDialog(
            onDismissRequest = {
                backPressed.value = false
            },
            title = {
                Text(text = "Are you sure you want to go back?")
            },
            text = {

            },
            confirmButton = {
                Button(
                    onClick = {
                        comparisonBox.put(comparison)
                        backPressed.value = false
                        navController.popBackStack()
                    }) {
                    Text("Continue")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        backPressed.value = false
                    }) {
                    Text("cancel")
                }
            }
        )
    }
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
            Button(onClick = {
                val comparisonBox = ObjectBox.store.boxFor(OBComparison::class.java)
                val compHolder = comparisonBox[compId]
                when(product.origin){
                    "coles"-> {
                        compHolder.colesProductId= 0L
                    }
                    "woolworths"-> {
                        compHolder.woolworthsProductId=0.toLong()
                    }
                    "aldi"-> {
                        compHolder.aldiProductId=0.toLong()
                    }
                    "iga"-> {
                        compHolder.igaProductId=0.toLong()
                    }
                    else-> {
                        Log.e("something went wrong", "comparison view, remove product, abnormal product origin")
                    }
                }
                comparisonBox.put(compHolder)
                navController.navigate(Routes.ComparisonView.Path+"/"+compId){
                    popUpTo(Routes.Home.Path)
                }
                //expanded=false

            }) {
                Text(text = "Remove Product")
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