package com.example.lab09.screens.detals

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lab09.model.MeasurementViewModel
import com.example.lab09.model.MeasurementViewModelFactory
import java.util.Date
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementDetailsScreen(
    navController: NavController,
    measurementId: String?,
    viewModel: MeasurementViewModel = viewModel(
        factory = MeasurementViewModelFactory(LocalContext.current)
    )
) {
    // Pobieramy obiekt Measurement z ViewModelu po ID (konwersja String -> Long)
    val measurement = viewModel.getMeasurementById(measurementId?.toLong())

    // Scaffold z TopAppBar dla standardowego wyglądu ekranu
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły pomiaru") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        // --- Sprawdzenie, czy pomiar istnieje ---
        if (measurement == null) {
            // Jeśli nie ma pomiaru o danym ID, pokazujemy informację
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nie znaleziono pomiaru")
            }
        } else {
            // --- Wyświetlenie szczegółów pomiaru ---
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Poziom hałasu
                Text(
                    "Hałas: ${measurement.noiseLevel}",
                    style = MaterialTheme.typography.titleMedium
                )
                // GPS: szerokość i długość geograficzna
                Text("GPS:")
                Text("Lat: ${measurement.latitude}")
                Text("Lon: ${measurement.longitude}")
                // Data i czas pomiaru
                Text("Data:")
                Text(Date(measurement.timestamp).toString())

                // --- Zdjęcie pomiaru ---
                if (measurement.photoPath != null) {
                    // Jeśli jest zdjęcie, wyświetlamy je z AsyncImage (Coil)
                    AsyncImage(
                        model = Uri.parse(measurement.photoPath),
                        contentDescription = "Zdjęcie pomiaru",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Jeśli brak zdjęcia, pokazujemy placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Brak zdjęcia")
                    }
                }
            }
        }
    }
}