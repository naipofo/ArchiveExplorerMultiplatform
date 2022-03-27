package com.naipofo.archiveclient.common.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import com.naipofo.archiveclient.common.ui.navigation.NavElement
import com.naipofo.archiveclient.common.ui.navigation.VisualNavElement
import com.naipofo.archiveclient.common.ui.routes.home.HomeRoute
import com.naipofo.archiveclient.common.ui.routes.settings.SettingsRoute

@Composable
fun AppNavigation() {
    NavElement(
        Destinations.Home,
        listOf(
            VisualNavElement(Icons.Default.Home, "Home", Destinations.Home),
            VisualNavElement(Icons.Default.Settings, "Settings", Destinations.Settings)
        )
    ) { current, _ ->
        when (current) {
            Destinations.Home -> HomeRoute()
            Destinations.Settings -> SettingsRoute()
        }
    }
}

sealed interface Destinations {
    object Home : Destinations
    object Settings : Destinations
}
