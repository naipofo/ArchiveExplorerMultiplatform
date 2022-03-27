package com.naipofo.archiveclient.common.ui.routes.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val searchBox: String = "",
    val results: RemoteResource<Result<List<SimpleSearchResult>>> = RemoteResource.Loading
)
@Composable
fun SearchRoute(){
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(SearchState()) }
    val searchRepository: SearchRepository by localDI().instance()

    fun loadResults() {
        if (state.searchBox != "") {
            state = state.copy(
                results = RemoteResource.Loading
            )
            scope.launch {
                val data = searchRepository.simpleSearch(state.searchBox)
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
                searchBox = it
            )
            loadResults()
        },
        onReload = { loadResults() }
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onSearchBoxChange: (String) -> Unit,
    onReload: () -> Unit
){
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxWidth().padding(8.dp)) {
            BasicTextField(state.searchBox, {onSearchBoxChange(it)}, Modifier.fillMaxWidth(), textStyle = MaterialTheme.typography.headlineMedium, maxLines = 1)
            if (state.searchBox == "") Text("Search...", color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f), style = MaterialTheme.typography.headlineMedium)
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