package com.naipofo.archiveclient.common

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.naipofo.archiveclient.common.di.mainModule
import org.kodein.di.compose.withDI

@Composable
fun App()  = withDI(mainModule) {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()

    Button(onClick = {
        text = "Hello, ${platformName}"
    }) {
        Text(text)
    }
}
