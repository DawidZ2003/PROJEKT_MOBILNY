package com.example.lab09.screens.detals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab09.model.Measurement
import java.util.Date

@Composable
fun MeasurementRow(
    measurement: Measurement,
    onClick: (Long) -> Unit,  // Callback po kliknięciu w całą kartę (np. otwarcie szczegółów)
    onDelete: (Long) -> Unit  // Callback po kliknięciu przycisku usuwania
) {
    // --- Card jako kontener wiersza pomiaru ---
    Card(
        modifier = Modifier
            .padding(8.dp) // margines wokół karty
            .fillMaxWidth() // zajmuje całą szerokość rodzica
            .clickable { onClick(measurement.id) } // kliknięcie całej karty
            .height(IntrinsicSize.Min), // minimalna wysokość zgodna z zawartością
        elevation = CardDefaults.cardElevation(6.dp) // delikatny cień karty
    ) {
        // --- Row dla układu tekst + ikona ---
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // --- Kolumna z danymi pomiaru ---
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f) // zajmuje całą szerokość oprócz przycisku
            ) {
                Text("Hałas: ${measurement.noiseLevel ?: "-"} dB", fontSize = 16.sp)
                Text("GPS: ${measurement.latitude}, ${measurement.longitude}", fontSize = 14.sp)
                Text("Data: ${Date(measurement.timestamp)}", fontSize = 12.sp)
            }
            // --- Przycisk usuwania pomiaru ---
            IconButton(
                onClick = { onDelete(measurement.id) },
                modifier = Modifier
                    .size(56.dp) // duży przycisk dotykowy
                    .background(Color.Red.copy(alpha = 0.2f), shape = CircleShape) // lekki czerwony background
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Usuń pomiar",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp) // duża ikona kosza
                )
            }
        }
    }
}