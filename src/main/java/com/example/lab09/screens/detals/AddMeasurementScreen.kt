package com.example.lab09.screens.detals

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.lab09.model.Measurement
import com.example.lab09.model.MeasurementViewModel
import com.example.lab09.model.getCurrentLocation
import com.example.lab09.model.getNoiseLevel
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeasurementScreen(
    navController: NavController,
    viewModel: MeasurementViewModel
) {
    val context = LocalContext.current
    // Przechowujemy tymczasowo dane GPS, poziom hałasu i URI zdjęcia
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var noiseLevel by remember { mutableStateOf<Float?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // --- Launchery do runtime permissions ---
    // 1. GPS
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // Jeśli uprawnienie przyznane, pobieramy lokalizację
            getCurrentLocation(context) { lat, lon ->
                latitude = lat
                longitude = lon
                viewModel.setLocation(lat, lon) // Aktualizacja w ViewModelu
            }
        } else {
            Toast.makeText(context, "Brak uprawnień do GPS", Toast.LENGTH_SHORT).show()
        }
    }

    // 2. Mikrofon / hałas
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            try {
                noiseLevel = getNoiseLevel(context)
                viewModel.setNoise(noiseLevel!!) // Aktualizacja w ViewModelu
            } catch (e: SecurityException) {
                e.printStackTrace()
                Toast.makeText(context, "Nie udało się zmierzyć hałasu", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Brak uprawnień do mikrofonu", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Kamera
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            viewModel.setPhoto(photoUri!!) // Aktualizacja w ViewModelu
        } else if (!success) {
            Toast.makeText(context, "Nie udało się zrobić zdjęcia", Toast.LENGTH_SHORT).show()
        }
    }

    // --- Layout ekranu ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nowy pomiar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Wróć")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- Przycisk GPS ---
            // Uruchamia launcher do żądania uprawnień i pobiera lokalizację
            Button(onClick = {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }) {
                Text("Pobierz GPS")
            }
            if (latitude != null && longitude != null) {
                Text("GPS: $latitude, $longitude") // Wyświetlenie pobranej lokalizacji
            }

            // --- Przycisk Hałas ---
            // Uruchamia pomiar dźwięku z mikrofonu
            Button(onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    try {
                        noiseLevel = getNoiseLevel(context)
                        viewModel.setNoise(noiseLevel!!)
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                        Toast.makeText(context, "Nie udało się zmierzyć hałasu", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }) {
                Text("Zmierz hałas")
            }
            noiseLevel?.let { Text("Hałas: ${"%.1f".format(it)} dB") }

            // --- Przycisk Aparat ---
            // Tworzy tymczasowy plik i uruchamia kamerę
            Button(onClick = {
                try {
                    val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
                    photoUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    cameraLauncher.launch(photoUri!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Nie udało się uruchomić aparatu", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Zrób zdjęcie")
            }
            photoUri?.let { Text("Zdjęcie gotowe: ${it.lastPathSegment}") }

            // --- Przycisk Zapis pomiaru ---
            // Tworzy nowy obiekt Measurement i dodaje go do ViewModelu
            Button(
                onClick = {
                    val newMeasurement = Measurement(
                        id = System.currentTimeMillis(),
                        latitude = latitude,
                        longitude = longitude,
                        noiseLevel = noiseLevel,
                        photoPath = photoUri?.toString()
                    )
                    viewModel.addMeasurement(newMeasurement)
                    navController.popBackStack() // Wracamy do poprzedniego ekranu
                },
                enabled = latitude != null && longitude != null && noiseLevel != null // Włączone tylko, gdy dane są kompletne
            ) {
                Text("Zapisz pomiar")
            }
        }
    }
}