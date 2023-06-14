package com.noxapps.grocerycomparer

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.noxapps.grocerycomparer.products.Product
import com.noxapps.grocerycomparer.ui.theme.GroceryComparerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.measureTimeMillis
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ObjectBox.init(this)
        setContent {
            GroceryComparerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavMain(navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val testString = LocalContext.current.assets.open("test.txt").bufferedReader().use {
        it.readLine()
    }
    Text(text = "Hello $testString!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GroceryComparerTheme {
        Greeting("Android")
    }
}


@Composable
fun NavMain(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Init.Path) {
        composable(Routes.Init.Path) { Init(navController = navController) }
        composable(Routes.Home.Path){Home(navController = navController)}

    }
}