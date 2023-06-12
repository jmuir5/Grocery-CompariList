package com.noxapps.grocerycomparer

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

@Composable
fun Init(viewModel: InitViewModel = InitViewModel(), navController: NavHostController) {
    viewModel.initialiseData(LocalContext.current)
    Text(text = "Initializing")

}