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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.noxapps.grocerycomparer.ui.theme.GroceryComparerTheme
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var localSettingsFile:File

        val storage = Firebase.storage
        val firebaseStorageRef = storage.reference
        val testTxt = firebaseStorageRef.child("test_firebase.txt")

        val colesTxt = firebaseStorageRef.child("products/colesProducts.txt")
        val woolworthsTxt = firebaseStorageRef.child("products/woolworthsProducts.txt")
        val aldiTxt = firebaseStorageRef.child("products/aldiProducts.txt")
        val igaTxt = firebaseStorageRef.child("products/igaProducts.txt")
        val firebaseArray = listOf(colesTxt, woolworthsTxt,aldiTxt,igaTxt)

        val colesDataFile = File(applicationContext.filesDir, "colesDataFile")
        val woolworthsDataFile = File(applicationContext.filesDir, "woolworthsDataFile")
        val aldiDataFile = File(applicationContext.filesDir, "aldiDataFile")
        val igaDataFile = File(applicationContext.filesDir, "igaDataFile")
        val dataFileArray = listOf(colesDataFile, woolworthsDataFile,aldiDataFile,igaDataFile)

        var initStatus = 0

        if(File(applicationContext.filesDir, "localTestFile").exists()) {
            Log.d("fileStatus", "file exists")
            localSettingsFile = File(applicationContext.filesDir, "localTestFile")
            val firebaseSettingsFile = File.createTempFile("firebaseSettingsFile", "txt")
            testTxt.getFile(firebaseSettingsFile).addOnSuccessListener {
                Log.d("download success", "succeded in downloading file from firebase")
                val localLines  = mutableListOf<String>()
                Files.lines(Paths.get(localSettingsFile.path), Charsets.UTF_8).forEach {
                    Log.d("local file lines", it)
                    localLines.add(it)
                }
                val firebaseLines  = mutableListOf<String>()
                Files.lines(Paths.get(firebaseSettingsFile.path), Charsets.UTF_8).forEach {
                    Log.d("firebase file lines", it)
                    firebaseLines.add(it)
                }
                if(localLines==firebaseLines) {
                    Log.d("file status", "files Match!")
                    initStatus = 1

                }
                else{
                    Log.d("file status", "files do not match!")
                    applicationContext.openFileOutput("localTestFile", Context.MODE_PRIVATE).use {
                        firebaseLines.forEach{ it2->
                            it.write(it2.toByteArray())
                        }
                    }
                    val localLines2  = mutableListOf<String>()
                    Files.lines(Paths.get(localSettingsFile.path), Charsets.UTF_8).forEach {
                        Log.d("local file lines", it)
                        localLines2.add(it)
                    }
                    if(localLines2==firebaseLines) {
                        Log.d("file status", "files  now Match!")
                        for (i in firebaseArray.indices){
                            dataFileArray[i].delete()
                            dataFileArray[i].createNewFile()
                            firebaseArray[i].getFile(dataFileArray[i]).addOnSuccessListener {
                                Log.d("download success", "succeded in downloading file from ${firebaseArray[i].name}")
                            }.addOnFailureListener {
                                Log.d("download failed", "failed to download file from ${firebaseArray[i].name}")// Handle any errors
                            }
                        }
                        Log.d("download status", "all downloads completed")
                        initStatus = 1


                    }
                    else {
                        Log.d("file status", "files still do not match!")
                        initStatus = -1
                    }

                }
            }.addOnFailureListener {
                Log.d("download failed", "failed to download file from firebase")// Handle any errors
                initStatus = -1
            }
        }
        else{
            Log.d("fileStatus", "file does not exist")
            localSettingsFile = File(applicationContext.filesDir, "localTestFile")
            testTxt.getFile(localSettingsFile).addOnSuccessListener {
                Log.d("download success", "succeded in downloading file from firebase")
                for (i in firebaseArray.indices){
                    firebaseArray[i].getFile(dataFileArray[i]).addOnSuccessListener {
                        Log.d("download success", "succeded in downloading file from ${firebaseArray[i].name}")
                    }.addOnFailureListener {
                        Log.d("download failed", "failed to download file from ${firebaseArray[i].name}")// Handle any errors
                    }
                }
                initStatus = 1
                Log.d("download status", "all downloads completed")
            }.addOnFailureListener {
                Log.d("download failed", "failed to download file from firebase")// Handle any errors
                initStatus = -1
            }


        }



        for(i in dataFileArray.indices){

        }
        setContent {
            GroceryComparerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
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