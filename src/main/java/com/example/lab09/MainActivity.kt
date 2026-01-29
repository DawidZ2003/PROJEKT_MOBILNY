package com.example.lab09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab09.ui.theme.LAB09Theme
import com.example.lab09.navigation.EnvironmentNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                EnvironmentNavigation()
            }
        }
    }
}
@Composable
fun MyApp(content: @Composable () -> Unit) {
    LAB09Theme {
        content()
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        EnvironmentNavigation()
    }
}