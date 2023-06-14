package com.noxapps.grocerycomparer

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

@Composable
fun Init(viewModel: InitViewModel = InitViewModel(), navController: NavHostController) {
    viewModel.navController = navController
    viewModel.initialiseData(LocalContext.current)
    Text(text = "Initializing")
    if (viewModel.initStatus==1){
        navController.navigate(Routes.Home.Path)
    }
}