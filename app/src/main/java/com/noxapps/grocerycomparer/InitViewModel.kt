package com.noxapps.grocerycomparer

import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.noxapps.grocerycomparer.products.Product
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureTimeMillis

class InitViewModel: ViewModel() {

    fun initialiseData(context:Context) {
        lateinit var localSettingsFile: File

        val storage = Firebase.storage
        val firebaseStorageRef = storage.reference
        val testTxt = firebaseStorageRef.child("test_firebase.txt")

        val colesTxt = firebaseStorageRef.child("products/colesProducts.txt")
        val woolworthsTxt = firebaseStorageRef.child("products/woolworthsProducts.txt")
        val aldiTxt = firebaseStorageRef.child("products/aldiProducts.txt")
        val igaTxt = firebaseStorageRef.child("products/igaProducts.txt")
        val firebaseArray = listOf(colesTxt, woolworthsTxt, aldiTxt, igaTxt)

        val colesDataFile = File(context.filesDir, "colesDataFile")
        val woolworthsDataFile = File(context.filesDir, "woolworthsDataFile")
        val aldiDataFile = File(context.filesDir, "aldiDataFile")
        val igaDataFile = File(context.filesDir, "igaDataFile")
        val dataFileArray = listOf(colesDataFile, woolworthsDataFile, aldiDataFile, igaDataFile)

        val productArray = mutableListOf<Product>()

        var initStatus = 0
        if (File(context.filesDir, "localTestFile").exists()) {
            Log.d("fileStatus", "file exists")
            localSettingsFile = File(context.filesDir, "localTestFile")
            val firebaseSettingsFile = File.createTempFile("firebaseSettingsFile", "txt")
            testTxt.getFile(firebaseSettingsFile).addOnSuccessListener {
                Log.d("download success", "succeded in downloading file from firebase")
                val localLines = mutableListOf<String>()
                Files.lines(Paths.get(localSettingsFile.path), Charsets.UTF_8).forEach {
                    Log.d("local file lines", it)
                    localLines.add(it)
                }
                val firebaseLines = mutableListOf<String>()
                Files.lines(Paths.get(firebaseSettingsFile.path), Charsets.UTF_8).forEach {
                    Log.d("firebase file lines", it)
                    firebaseLines.add(it)
                }
                if (localLines == firebaseLines) {
                    Log.d("file status", "files Match!")
                    initStatus = 1
                    buildArrays(dataFileArray, productArray)
                    findMother(productArray)

                } else {
                    Log.d("file status", "files do not match!")
                    context.openFileOutput("localTestFile", Context.MODE_PRIVATE)
                        .use {
                            firebaseLines.forEach { it2 ->
                                it.write(it2.toByteArray())
                            }
                        }
                    val localLines2 = mutableListOf<String>()
                    Files.lines(Paths.get(localSettingsFile.path), Charsets.UTF_8).forEach {
                        Log.d("local file lines", it)
                        localLines2.add(it)
                    }
                    if (localLines2 == firebaseLines) {
                        Log.d("file status", "files  now Match!")
                        for (i in firebaseArray.indices) {
                            dataFileArray[i].delete()
                            dataFileArray[i].createNewFile()
                            firebaseArray[i].getFile(dataFileArray[i]).addOnSuccessListener {
                                Log.d(
                                    "download success",
                                    "succeded in downloading file from ${firebaseArray[i].name}"
                                )
                                if (i == 3) {
                                    Log.d("download status", "all downloads completed")
                                    buildArrays(dataFileArray, productArray)
                                    findMother(productArray)
                                }
                            }.addOnFailureListener {
                                Log.d(
                                    "download failed",
                                    "failed to download file from ${firebaseArray[i].name}"
                                )// Handle any errors
                            }
                        }
                        initStatus = 1


                    } else {
                        Log.d("file status", "files still do not match!")
                        initStatus = -1
                    }

                }
            }.addOnFailureListener {
                Log.d(
                    "download failed",
                    "failed to download file from firebase"
                )// Handle any errors
                initStatus = -1
            }
        } else {
            Log.d("fileStatus", "file does not exist")
            localSettingsFile = File(context.filesDir, "localTestFile")
            testTxt.getFile(localSettingsFile).addOnSuccessListener {
                Log.d("download success", "succeded in downloading file from firebase")
                for (i in firebaseArray.indices) {
                    firebaseArray[i].getFile(dataFileArray[i]).addOnSuccessListener {
                        Log.d(
                            "download success",
                            "succeded in downloading file from ${firebaseArray[i].name}"
                        )
                        if (i == 3) {
                            Log.d("download status", "all downloads completed")
                            buildArrays(dataFileArray, productArray)
                            findMother(productArray)
                        }
                    }.addOnFailureListener {
                        Log.d(
                            "download failed",
                            "failed to download file from ${firebaseArray[i].name}"
                        )// Handle any errors
                    }
                }
                initStatus = 1

            }.addOnFailureListener {
                Log.d(
                    "download failed",
                    "failed to download file from firebase"
                )// Handle any errors
                initStatus = -1
            }
        }
    }
}



fun buildArrays(dataFileArray:List<File>, productArray:MutableList<Product>){
    Log.d("time measurement", "begin measuring time")
    val buildTime = measureTimeMillis {
        for (i in dataFileArray.indices) {
            dataFileArray[i].bufferedReader().lines().forEach {
                //Log.d("txt text", it.split(",").toString())
                if (it!="\n") {
                    when (i) {
                        0 -> productArray.add(
                            Product(
                                it.split(",")[0],
                                it.split(",")[1], it.split(",")[2],
                                it.split(",")[3], -1,//it.split(",")[4].toInt(),
                                "coles",
                            )
                        )
                        1 -> productArray.add(
                            Product(
                                it.split(",")[0],
                                it.split(",")[1], it.split(",")[2],
                                it.split(",")[3], it.split(",")[4].toLong(),
                                origin = "Woolworths",
                            )
                        )
                        2 -> productArray.add(
                            Product(
                                it.split(",")[0],
                                it.split(",")[1], it.split(",")[2],
                                it.split(",")[3], -1,
                                "aldi",
                            )
                        )
                        3 -> {
                            try {
                                productArray.add(
                                    Product(
                                        it.split(";")[0],
                                        it.split(";")[1], it.split(";")[2],
                                        it.split(";")[3], it.split(";")[4].toLong(),
                                        "iga",
                                    )
                                )
                            } catch (e:Exception){
                                Log.d("iga problem line", e.toString())
                                Log.d("iga problem line", it.split(";").toString())
                                Log.d("iga problem line", it.toString())

                            }

                        }
                    }
                }
            }
            Log.d("time measurement", "shop "+i+" done")
        }
    }
    Log.d("time measurement", "total elapsed time: "+buildTime)
}

fun findMother(productArray:MutableList<Product>){
    val returnArray = mutableListOf<Product>()
    val searchTime = measureTimeMillis {
        for(i in productArray){
            if(i.name.contains("mother"))returnArray.add(i)
        }
    }

    Log.d("search time", "total elapsed time: "+searchTime)

}