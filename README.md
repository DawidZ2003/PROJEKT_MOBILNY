# 📱 PROJEKT_MOBILNY

Aplikacja mobilna na system **Android** służąca do **zbierania i zarządzania pomiarami środowiskowymi**.  
Umożliwia rejestrowanie **lokalizacji GPS**, **poziomu hałasu** z mikrofonu oraz dokumentowanie pomiarów **zdjęciem z aparatu**.

Projekt został stworzony w **Kotlinie** z wykorzystaniem **Jetpack Compose** oraz **ViewModel**.

---

## ✨ Funkcjonalności

- 📍 Pobieranie aktualnej lokalizacji GPS  
- 🔊 Pomiar poziomu hałasu z mikrofonu  
- 📷 Wykonywanie zdjęć dla każdego pomiaru  
- 📋 Lista zapisanych pomiarów  
- 🔍 Podgląd szczegółów pojedynczego pomiaru  
- 🗑 Usuwanie pojedynczych lub wszystkich pomiarów  
- 💾 Zapis danych do pliku JSON  
- 📤 Eksport pomiarów e-mailem jako załącznik w formacie JSON  

---

## 📡 Użyte sensory i źródła danych

Aplikacja wykorzystuje następujące sensory oraz systemowe źródła danych dostępne na urządzeniach z systemem Android.

### 📍 Lokalizacja (GPS)
- **Źródło:** Fused Location Provider (Google Play Services)  
- **Dane:** szerokość i długość geograficzna (*latitude, longitude*)  
- **Zastosowanie:** określenie miejsca wykonania pomiaru  
- **Wymagane uprawnienia:**  
  - `ACCESS_FINE_LOCATION`  
  - `ACCESS_COARSE_LOCATION`  

---

### 🔊 Mikrofon (czujnik dźwięku)
- **Źródło:** mikrofon urządzenia (`AudioRecord`)  
- **Dane:** poziom natężenia dźwięku  
- **Zastosowanie:** pomiar poziomu hałasu otoczenia  
- **Wymagane uprawnienia:**  
  - `RECORD_AUDIO`

---

## 📋 Główny ekran aplikacji
Widok listy zapisanych pomiarów środowiskowych.

<p align="center">
  <img src="https://github.com/user-attachments/assets/9674187f-993c-4048-9b80-ca981a137602" width="330"/>
</p>

---

## ➕ Ekran dodawania nowego pomiaru
Formularz umożliwiający zapis nowego pomiaru wraz z lokalizacją, poziomem hałasu i zdjęciem.

<p align="center">
  <img src="https://github.com/user-attachments/assets/ebec1e95-0a90-401a-922e-fa23855ce776" width="330"/>
</p>

---

## 🔍 Ekran szczegółów pomiaru
Szczegółowe informacje dotyczące wybranego pomiaru.

<p align="center">
  <img src="https://github.com/user-attachments/assets/0376eadb-240c-4541-8f5a-7f2c1da5e44f" width="330"/>
</p>

---

## 📤 Eksport pomiarów e-mailem
Wysyłanie zapisanych danych jako załącznik w formacie JSON.

<p align="center">
  <img src="https://github.com/user-attachments/assets/e71ef6a1-dd9f-4c65-8287-2d451a052150" width="330"/>
</p>

---

## 🚀 Uruchomienie projektu

1. Otwórz projekt w **Android Studio**  
2. Zbuduj aplikację:  
   **Build → Build APK**  
3. Zainstaluj plik `app-debug.apk` na emulatorze lub urządzeniu fizycznym  
4. Przyznaj wymagane uprawnienia:
   - Lokalizacja  
   - Mikrofon  
   - Aparat  

---

