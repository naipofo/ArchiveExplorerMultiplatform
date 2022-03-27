package com.naipofo.archiveclient.common

import androidx.compose.runtime.Composable
import com.naipofo.archiveclient.common.di.mainModule
import com.naipofo.archiveclient.common.ui.AppNavigation
import org.kodein.di.compose.withDI

@Composable
fun App() = withDI(mainModule) { AppNavigation() }
