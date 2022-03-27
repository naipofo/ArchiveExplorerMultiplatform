package com.naipofo.archiveclient.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.naipofo.archiveclient.common.di.mainModule
import com.naipofo.archiveclient.common.ui.navigation.NavElement
import com.naipofo.archiveclient.common.ui.navigation.VisualNavElement
import org.kodein.di.compose.withDI

@Composable
fun App() = withDI(mainModule) {
    NavElement(
        Destinations.Home,
        listOf(
            VisualNavElement(Icons.Default.Home, "Home", Destinations.Home),
            VisualNavElement(Icons.Default.Settings, "Settings", Destinations.Settings)
        )
    ) { current, _ ->
        when (current) {
            Destinations.Home -> Text("Home")
            Destinations.Settings -> Text("Settings")
        }
    }
}

sealed interface Destinations {
    object Home : Destinations
    object Settings : Destinations
}
