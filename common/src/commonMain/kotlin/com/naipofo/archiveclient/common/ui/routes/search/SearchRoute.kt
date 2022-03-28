package com.naipofo.archiveclient.common.ui.routes.search

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.unit.dp
import com.naipofo.archiveclient.common.data.RemoteResource
import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleSearchResult
import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.SearchRepository
import com.naipofo.archiveclient.common.ui.generic.ErrorElement
import com.naipofo.archiveclient.common.ui.generic.LoadingElement
import com.naipofo.archiveclient.common.ui.generic.elementFromResource
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance

data class SearchState(
    val searchBoxContent: String = "",
    val searchBoxState: SearchBoxState = SearchBoxState.Closed,
    val results: RemoteResource<Result<List<SimpleSearchResult>>> = RemoteResource.Loading
)

enum class SearchBoxState{
    Closed, Open
}

@Composable
fun SearchRoute(){
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(SearchState()) }
    val searchRepository: SearchRepository by localDI().instance()

    fun loadResults() {
        if (state.searchBoxContent != "") {
            state = state.copy(
                results = RemoteResource.Loading
            )
            scope.launch {
                val data = searchRepository.simpleSearch(state.searchBoxContent)
                state = state.copy(
                    results = RemoteResource.Success(data)
                )
            }
        }
    }

    SearchScreen(
        state = state,
        onSearchBoxChange = {
            state = state.copy(
                searchBoxContent = it
            )
            loadResults()
        },
        onReload = { loadResults() },
        onSearchBoxFocus = {
            state = state.copy(
                searchBoxState = SearchBoxState.Open
            )
        },
        onSearchBoxLostFocus = {
            state = state.copy(
                searchBoxState = SearchBoxState.Closed
            )
        }
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onSearchBoxChange: (String) -> Unit,
    onSearchBoxFocus: () -> Unit,
    onSearchBoxLostFocus: () -> Unit,
    onReload: () -> Unit
){
    Column(Modifier.fillMaxSize()) {
        val transition = updateTransition(state.searchBoxState)
        val corners by transition.animateInt {
            when(it){
                SearchBoxState.Closed -> 50
                SearchBoxState.Open -> 0
            }
        }
        val padding by transition.animateDp {
            when(it){
                SearchBoxState.Closed -> 8.dp
                SearchBoxState.Open -> 0.dp
            }
        }
        Box(
            Modifier.fillMaxWidth()
                .padding(padding)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(corners)
                )
                .padding(8.dp - padding + 8.dp)
        ) {
            BasicTextField(
                state.searchBoxContent,
                { onSearchBoxChange(it) },
                Modifier.fillMaxWidth()
                    .onFocusEvent {
                        if (it.hasFocus) onSearchBoxFocus() else onSearchBoxLostFocus()
                    },
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            if (state.searchBoxContent == "") Text(
                "Search...",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        LazyColumn(Modifier.weight(1f)) {
            elementFromResource(
                resource = state.results,
                loading = { item { LoadingElement(Modifier.fillMaxWidth().padding(16.dp)) } },
                error = { item { ErrorElement(it.message.toString(), { onReload() }, Modifier.padding(16.dp)) } },
                content = { items(it){ result ->
                    Text(result.identifier)
                } }
            )
        }
    }
}