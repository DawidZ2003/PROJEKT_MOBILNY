package com.example.lab09.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Fabryka ViewModeli do tworzenia instancji MeasurementViewModel z kontekstem
class MeasurementViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    // Metoda wywoływana przez ViewModelProvider przy tworzeniu ViewModelu
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Sprawdzamy, czy żądany ViewModel to MeasurementViewModel
        if (modelClass.isAssignableFrom(MeasurementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Tworzymy instancję MeasurementViewModel, przekazując kontekst aplikacji
            return MeasurementViewModel(context.applicationContext) as T
        }
        // Jeśli ktoś próbuje utworzyć inny ViewModel, rzucamy wyjątek
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}