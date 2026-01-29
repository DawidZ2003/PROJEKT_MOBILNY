package com.example.lab09.model

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.google.gson.Gson
import java.io.File

// Funkcja zapisuje listę pomiarów do pliku JSON w pamięci wewnętrznej aplikacji
fun saveMeasurementsToFile(context: Context, measurements: List<Measurement>): File {
    val gson = Gson() // Tworzymy instancję Gson do serializacji obiektów
    val json = gson.toJson(measurements) // Zamieniamy listę pomiarów na JSON
    // Tworzymy plik w katalogu filesDir aplikacji (bezpieczny katalog prywatny)
    val file = File(context.filesDir, "measurements.json")
    file.writeText(json) // Zapisujemy JSON do pliku
    return file // Zwracamy referencję do zapisanego pliku
}

// Funkcja wysyła plik (JSON) jako załącznik w e-mailu
fun exportFileByEmail(context: Context, file: File) {
    // Tworzymy bezpieczny URI pliku przy użyciu FileProvider
    val uri = FileProvider.getUriForFile(
        context,
        context.packageName + ".fileprovider", // Zgodnie z konfiguracją w manifest
        file
    )
    // Tworzymy Intent do wysyłki e-maila z załącznikiem
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/json" // Typ MIME dla JSON
        putExtra(Intent.EXTRA_SUBJECT, "Eksport pomiarów środowiskowych") // Temat e-maila
        putExtra(Intent.EXTRA_TEXT, "W załączniku przesyłam plik z pomiarami.") // Treść e-maila
        putExtra(Intent.EXTRA_STREAM, uri) // Dodanie załącznika
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Zezwalamy odbiorcy na odczyt pliku
    }
    // Uruchamiamy wybór aplikacji do wysyłki e-maila
    context.startActivity(Intent.createChooser(emailIntent, "Wyślij e-mailem"))
}

// Funkcja wczytuje listę pomiarów z pliku JSON w pamięci wewnętrznej
fun loadMeasurementsFromFile(context: Context): List<Measurement> {
    val file = File(context.filesDir, "measurements.json")
    if (!file.exists()) return emptyList() // Jeśli plik nie istnieje, zwracamy pustą listę
    val json = file.readText() // Odczytujemy zawartość pliku
    // Deserializacja JSON do tablicy Measurement, a następnie konwersja na listę
    return Gson().fromJson(json, Array<Measurement>::class.java).toList()
}