package com.example.product_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.product_front.ui.theme.Product_frontTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.product_front.ScanScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceiptApp()
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ReceiptApp() {
        val navController = rememberNavController()
        Product_frontTheme () {
            Scaffold(
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            MainScreen { navController.navigate("scan_screen") }
                        }
                        composable("scan_screen") {
                            ScanScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(onScanButtonClicked: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting("ここにレシピ情報")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onScanButtonClicked) {
                Text(text = "Scan")
            }
        }
    }
}

@Composable
fun Greeting(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "$text!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Product_frontTheme {
        MainScreen {}
    }
}
