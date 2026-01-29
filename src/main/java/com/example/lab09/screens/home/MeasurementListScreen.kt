package com.example.lab09.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab09.model.MeasurementViewModel
import com.example.lab09.model.exportFileByEmail
import com.example.lab09.model.saveMeasurementsToFile
import com.example.lab09.navigation.EnvironmentScreens
import com.example.lab09.screens.detals.MeasurementRow
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementListScreen(
    navController: NavController,
    viewModel: MeasurementViewModel
) {
    val state by viewModel.uiState // Obserwujemy stan UI z ViewModelu
    val context = LocalContext.current
    // --- Scaffold główny ekran ---
    Scaffold(
        // --- TopAppBar ---
        topBar = {
            TopAppBar(
                title = { Text("Pomiary środowiskowe") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        },
        // --- BottomAppBar z akcjami ---
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 1. Usuń wszystkie pomiary
                    IconButton(
                        onClick = { viewModel.deleteAll() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Usuń wszystkie",
                            tint = Color.Red,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    // 2. Dodaj nowy pomiar (nawigacja do AddMeasurementScreen)
                    IconButton(
                        onClick = { navController.navigate(EnvironmentScreens.AddMeasurement.name) },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Dodaj pomiar",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    // 3. Zapis listy pomiarów do pliku JSON
                    IconButton(
                        onClick = {
                            if (state.measurements.isNotEmpty()) {
                                val file = saveMeasurementsToFile(context, state.measurements)
                                Toast.makeText(
                                    context,
                                    "Zapisano do pliku: ${file.absolutePath}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(context, "Brak pomiarów do zapisu", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Save,
                            contentDescription = "Zapisz do pliku",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    // 4. Eksport pliku JSON e-mailem
                    IconButton(
                        onClick = {
                            val file = File(context.filesDir, "measurements.json")
                            if (file.exists()) {
                                exportFileByEmail(context, file)
                            } else {
                                Toast.makeText(context, "Najpierw zapisz plik", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Eksportuj e-mailem",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
        // --- Główna zawartość ekranu ---
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                if (state.measurements.isEmpty()) {
                    // Gdy brak pomiarów, wyświetlamy komunikat
                    Text(
                        "Brak pomiarów",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    // Lista pomiarów w LazyColumn
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.measurements) { measurement ->
                            MeasurementRow(
                                measurement = measurement,
                                onClick = { id ->
                                    // Nawigacja do szczegółów pomiaru
                                    navController.navigate("${EnvironmentScreens.MeasurementDetails.name}/$id")
                                },
                                onDelete = { id ->
                                    // Usuwanie pojedynczego pomiaru
                                    viewModel.deleteMeasurement(id)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}