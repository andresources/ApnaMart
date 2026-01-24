package com.apnamart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.apnamart.core.common.NetworkObserver
import com.apnamart.core.common.NetworkStatus
import com.apnamart.core.presentation.components.NoInternetBannerWithRetry
import com.apnamart.navigation.AppNavGraph
import com.apnamart.ui.theme.ApnaMartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen() // Android 12 system splash

        setContent {
            val context = LocalContext.current
            val networkObserver = remember { NetworkObserver(context) }
            val networkStatus by networkObserver.networkStatus.collectAsState()
            ApnaMartTheme {
                    Box{
                        AppNavGraph()
                        if (networkStatus is NetworkStatus.Unavailable) {
                            NoInternetBannerWithRetry(
                                modifier = Modifier.align(Alignment.TopCenter),
                                onRetry = {
                                    networkObserver.checkNow()
                                }
                            )
                        }
                    }

            }
        }
    }
}


