package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android2",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Hello $name! say = "+inputText,
            modifier = modifier
        )

        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
            }

        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    _157LabLearnAndroidTheme {
        Greeting2("Android")
    }
}