package com.noxapps.grocerycomparer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Button

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
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
            Button(modifier = Modifier.fillMaxSize(),
                onClick = {
                    //comparisonBox.put(OBProduct(id, colesP, wolliesP,aldiP, igaP))
                    navController.popBackStack()
                }){
                    Text(text = "Save Comparison")
            }
        }
    )
}