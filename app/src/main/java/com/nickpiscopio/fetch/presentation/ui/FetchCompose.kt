import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickpiscopio.fetch.domain.model.ListItem
import com.nickpiscopio.fetch.presentation.constants.COLOR_WHITE_100
import com.nickpiscopio.fetch.presentation.constants.DIMEN_16
import com.nickpiscopio.fetch.presentation.constants.DIMEN_2
import com.nickpiscopio.fetch.presentation.constants.DIMEN_8
import com.nickpiscopio.fetch.presentation.ui.fragments.LoadingIndicator
import com.nickpiscopio.fetch.presentation.ui.theme.FetchTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun FetchCompose(viewModel: FetchComposeViewModel = FetchComposeViewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (viewModel.isLoading.value) {
            LoadingIndicator(viewModel.isLoading)
        } else {
            FetchContent(viewModel)
        }
    }

}

@Composable
fun FetchContent(viewModel: FetchComposeViewModel) {
    val error by viewModel.error.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            error != null -> Text("Error: $error")
            else -> FetchContentList(viewModel)
        }
    }
}

@Composable
fun FetchContentList(viewModel: FetchComposeViewModel) {
    val listItems by viewModel.listItems.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(DIMEN_16),
        verticalArrangement = Arrangement.spacedBy(DIMEN_8)
    ) {
        items(listItems.count()) { index ->
            val listItem = listItems[index]
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(DIMEN_2),
                colors = CardDefaults.cardColors(
                    containerColor = COLOR_WHITE_100
                )
            ) {
                Column(modifier = Modifier.padding(DIMEN_16)) {
                    Text("ID: ${listItem.id}")
                    Text("List ID: ${listItem.listId}")
                    Text("Name: ${listItem.name}")
                }
            }
        }
    }
}

class FetchComposeViewModel(private val repository: ListItemRepository = ListItemRepository()) :
    ViewModel() {
    val isLoading: MutableState<Boolean> = mutableStateOf(true)

    private val _listItems = MutableStateFlow<List<ListItem>>(emptyList())
    val listItems: StateFlow<List<ListItem>> = _listItems.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getListItems()
    }

    private fun getListItems() {
        isLoading.value = true

        viewModelScope.launch {
            val result = repository.getListItems()
            result
                .onSuccess { items ->
                    val filteredSortedGrouped = items
                        .filter { !it.name.isNullOrBlank() }
                        .sortedWith(compareBy({ it.listId }, { it.name }))
                        .groupBy { it.listId }

                    val groupedList = filteredSortedGrouped.values.flatten()

                    _listItems.value = groupedList
                }
                .onFailure { _error.value = it.localizedMessage }
            isLoading.value = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEntry() {
    FetchTheme {
        FetchCompose()
    }
}