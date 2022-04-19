package com.naipofo.archiveclient.common.ui.routes.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.naipofo.archiveclient.common.cashedImageFromUrl
import com.naipofo.archiveclient.common.data.RemoteResource
import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleSuggestion
import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.SearchRepository
import com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions.SuggestionRepository
import com.naipofo.archiveclient.common.ui.generic.ErrorElement
import io.ktor.client.*
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import kotlin.math.floor

data class SearchState(
    val suggestions: RemoteResource<Result<List<SimpleSuggestion>>> = RemoteResource.Loading,
    val images: List<ImageBitmap?> = listOf()
)

@Composable
fun SearchRoute() {
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(SearchState()) }
    val searchRepository: SearchRepository by localDI().instance()
    val suggestionRepository: SuggestionRepository by localDI().instance()

    fun loadSuggestions() = scope.launch {
        state = state.copy(
            suggestions = RemoteResource.Success(suggestionRepository.getSuggestions(1))
        )
    }
    SideEffect { if (state.suggestions == RemoteResource.Loading) loadSuggestions() }
    SearchScreen(
        state = state,
        onReloadSuggestions = { loadSuggestions() },
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onReloadSuggestions: () -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val boxWidth = maxWidth
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier.weight(1f)
            ) {
                when (state.suggestions) {
                    RemoteResource.Loading -> item { Text("Loading suggestions...") }
                    is RemoteResource.Success -> when (val result = state.suggestions.data) {
                        is Result.Success -> {
                            val perLine = floor(((boxWidth - 16.dp) / 308.dp)).toInt()
                            items(result.data.size) { rowIndex ->
                                Row {
                                    for (i in 0..perLine) {
                                        val itemIndex = rowIndex * perLine + i
                                        if (itemIndex >= result.data.size) {
                                            Box(Modifier.weight(1f)) { }
                                        } else {
                                            val item = result.data[itemIndex]
                                            Box(Modifier.weight(1f).height(220.dp).padding(8.dp)) {
                                                val scope = rememberCoroutineScope()
                                                val client: HttpClient by localDI().instance()
                                                var image by remember { mutableStateOf<ImageBitmap?>(null) }
                                                try {
                                                    scope.launch {
                                                        image =
                                                            cashedImageFromUrl(
                                                                "https://archive.org/services/img/${item.identifier}",
                                                                client
                                                            )
                                                    }
                                                } catch (_: Exception) {
                                                }
                                                image?.let {
                                                    Image(
                                                        it,
                                                        "",
                                                        Modifier.matchParentSize().clip(
                                                            RoundedCornerShape(16.dp)
                                                        ),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                                Column(
                                                    Modifier.matchParentSize().background(
                                                        Brush.verticalGradient(
                                                            0f to MaterialTheme.colorScheme.onBackground.copy(alpha = .25f),
                                                            .5f to MaterialTheme.colorScheme.onBackground.copy(alpha = .25f),
                                                            1f to MaterialTheme.colorScheme.background.copy(alpha = .5f),
                                                        ),
                                                        RoundedCornerShape(16.dp)
                                                    ).padding(16.dp), verticalArrangement = Arrangement.Bottom
                                                ) {
                                                    Text(
                                                        item.title,
                                                        style = MaterialTheme.typography.labelLarge,
                                                        maxLines = 1
                                                    )
                                                    Text(
                                                        item.identifier,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontFamily = FontFamily.Monospace,
                                                        maxLines = 1
                                                    )
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        is Result.Error -> item {
                            ErrorElement("Error loading suggestions:\n${result.exception}",
                                onReload = { onReloadSuggestions() })
                        }
                    }
                }
            }
        }
    }
}