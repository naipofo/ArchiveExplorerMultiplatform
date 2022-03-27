package com.naipofo.archiveclient.common.ui.navigation

import androidx.compose.runtime.mutableStateOf

class NavController<DestinationBase>(beginning: DestinationBase) {
    private val backStack = mutableListOf(beginning)
    fun pop() {
        // size == 1 should never happen
        if (backStack.size > 1) backStack.removeLast()
        currentBackStackEntry.value = backStack.last()
    }

    var currentBackStackEntry = mutableStateOf(beginning)


    fun navigate(destination: DestinationBase) {
        backStack.add(destination)
        currentBackStackEntry.value = destination
    }

    fun absoluteNavigate(destination: DestinationBase) {
        backStack.clear()
        navigate(destination)
    }
}