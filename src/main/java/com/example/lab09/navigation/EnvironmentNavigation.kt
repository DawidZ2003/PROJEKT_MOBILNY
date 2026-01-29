package com.example.lab09.navigation

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab09.model.MeasurementViewModel
import com.example.lab09.model.MeasurementViewModelFactory
import com.example.lab09.screens.detals.AddMeasurementScreen
import com.example.lab09.screens.detals.MeasurementDetailsScreen
import com.example.lab09.screens.home.MeasurementListScreen

@SuppressLint("ContextCastToActivity")
@Composable
fun EnvironmentNavigation() {
    val navController = rememberNavController()
    val activity = LocalContext.current as ComponentActivity
    val measurementViewModel: MeasurementViewModel = viewModel(
        modelClass = MeasurementViewModel::class.java,
        viewModelStoreOwner = activity,
        factory = MeasurementViewModelFactory(activity.applicationContext)
    )
    NavHost(
        navController = navController,
        startDestination = EnvironmentScreens.MeasurementList.name
    ) {
        composable(EnvironmentScreens.MeasurementList.name) {
            MeasurementListScreen(
                navController = navController,
                viewModel = measurementViewModel
            )
        }
        composable(EnvironmentScreens.AddMeasurement.name) {
            AddMeasurementScreen(
                navController = navController,
                viewModel = measurementViewModel
            )
        }
        composable(
            route = "${EnvironmentScreens.MeasurementDetails.name}/{measurementId}"
        ) { backStackEntry ->
            MeasurementDetailsScreen(
                navController = navController,
                measurementId = backStackEntry.arguments?.getString("measurementId"),
                viewModel = measurementViewModel
            )
        }
    }
}