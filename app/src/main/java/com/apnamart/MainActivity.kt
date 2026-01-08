package com.apnamart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.apnamart.navigation.AppNavGraph
import com.apnamart.ui.theme.ApnaMartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen() // Android 12 system splash
        setContent {
            ApnaMartTheme {
                    Box{
                        AppNavGraph()
                    }
            }
        }
    }
}


