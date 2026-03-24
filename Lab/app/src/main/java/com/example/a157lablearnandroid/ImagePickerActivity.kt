package com.example.a157lablearnandroid

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage

class ImagePickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagePickerScreen()
        }
    }
}

@Composable
fun ImagePickerScreen() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val permissionCheckResult = ContextCompat.checkSelfPermission(
                context,
                permissionToRequest
            )
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                galleryLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(permissionToRequest)
            }
        }) {
            Text("เลือกรูปภาพ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Selected Image",
                modifier = Modifier.size(300.dp)
            )
        }
    }
}
