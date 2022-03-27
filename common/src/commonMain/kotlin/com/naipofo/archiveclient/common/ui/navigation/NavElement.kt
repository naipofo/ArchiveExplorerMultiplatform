package com.naipofo.archiveclient.common.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun <T> NavElement(default: T, main: List<VisualNavElement<T>>, content: @Composable (T, NavController<T>) -> Unit) {
    val controller by remember { mutableStateOf(NavController(default)) }
    var selectedItem by remember { mutableStateOf(0) }

    @Composable
    fun display() = content(controller.currentBackStackEntry.value, controller)
    BoxWithConstraints {
        if (maxWidth < 400.dp) {
            Column {
                Box(Modifier.weight(1f)) {
                    display()
                }
                NavigationBar {
                    main.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.title, maxLines = 1) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                controller.absoluteNavigate(item.destination)
                            }
                        )
                    }
                }
            }
        } else {
            Row {
                NavigationRail {
                    main.forEachIndexed { index, item ->
                        NavigationRailItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.title) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                controller.absoluteNavigate(item.destination)
                            }
                        )
                    }
                }
                Box(Modifier.weight(1f)) {
                    display()
                }
            }
        }
    }

}

data class VisualNavElement<T>(
    val icon: ImageVector,
    val title: String,
    val destination: T
)