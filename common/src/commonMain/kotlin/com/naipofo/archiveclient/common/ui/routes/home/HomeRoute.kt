package com.naipofo.archiveclient.common.ui.routes.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naipofo.archiveclient.common.data.RemoteResource
import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleHeadline
import com.naipofo.archiveclient.common.ui.generic.ErrorElement
import com.naipofo.archiveclient.common.ui.generic.LoadingElement
import com.naipofo.archiveclient.common.ui.generic.elementFromResource
import kotlinx.coroutines.launch

data class HomeState(
    val headlines: RemoteResource<Result<List<SimpleHeadline>>> = RemoteResource.Loading
)

@Composable
fun HomeRoute() {
    val scope = rememberCoroutineScope()
    var homeState = HomeState()

    fun loadData() = scope.launch {
        homeState = HomeState(
            RemoteResource.Loading //TODO: Load headlines
        )
    }

    SideEffect { if (homeState.headlines is RemoteResource.Loading) loadData() }

    HomeScreen(
        state = homeState,
        onReload = { loadData() },
        onNewsClick = {/* TODO: open in browser */ }
    )
}

@Composable
fun HomeScreen(state: HomeState, onReload: () -> Unit, onNewsClick: (url: String) -> Unit) {
    LazyColumn(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        item {
            Text("Archive Explorer!")
        }
        elementFromResource(
            state.headlines,
            loading = { item { LoadingElement(Modifier.fillMaxWidth().height(50.dp)) } },
            error = { item { ErrorElement(it.message.toString(), { onReload() }, Modifier.fillMaxWidth()) } },
            content = {
                items(it) { headline ->
                    Row(Modifier.padding(8.dp).padding(start = 16.dp).fillMaxWidth()) {
                        Text(
                            headline.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1F)
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.Top),
                            onClick = { onNewsClick(headline.webUrl) }
                        ) {
                            Icon(Icons.Default.OpenInBrowser, "Open in browser")
                        }
                    }
                }
            }
        )
    }
}