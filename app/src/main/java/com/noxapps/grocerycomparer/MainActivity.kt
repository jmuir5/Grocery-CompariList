package com.noxapps.grocerycomparer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.noxapps.grocerycomparer.ui.theme.GroceryComparerTheme
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