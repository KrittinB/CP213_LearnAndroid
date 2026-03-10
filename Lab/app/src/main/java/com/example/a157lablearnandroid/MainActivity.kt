package com.example.a157lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            startActivity(
                Intent(this@MainActivity, ListActivity::class.java)
            )

            CharacterScreen()


            }
    }
}
@Preview

@Composable
fun CharacterScreen() {


    var str by remember { mutableStateOf(8) }
    var agi by remember { mutableStateOf(10) }
    var intStat by remember { mutableStateOf(15) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(32.dp)
    ) {

        // HP BAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(41.dp)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight()
                    .background(Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.puppy),
            contentDescription = "Profile",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
                .clickable {
                    //startActivity(Intent(this@MainActivity, ListActivity::class.java))



                }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Button(onClick = { str++ }) {
                    Text("+", fontSize = 32.sp)
                }
                Text("Str", fontSize = 32.sp)
                Text(str.toString(), fontSize = 32.sp)
            }

            Column {
                Button(onClick = { agi++ }) {
                    Text("+", fontSize = 32.sp)
                }
                Text("Agi", fontSize = 32.sp)
                Text(agi.toString(), fontSize = 32.sp)
            }

            Column {
                Button(onClick = { intStat++ }) {
                    Text("+", fontSize = 32.sp)
                }
                Text("Int", fontSize = 32.sp)
                Text(intStat.toString(), fontSize = 32.sp)
            }
        }
    }
}
