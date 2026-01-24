package com.apnamart

import android.os.Build
import android.os.Bundle
import android.view.View
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
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashView ->
                splashView.remove() // instantly removes splash
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            //controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // Android 10 and below (fallback, deprecated but needed)
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
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


