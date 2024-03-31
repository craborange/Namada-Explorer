package namada.crab_orange.namada_explorer.ui.block_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import namada.crab_orange.namada_explorer.apis.IndexerApis
import namada.crab_orange.namada_explorer.data.Block
import javax.inject.Inject

@HiltViewModel
class BlockDetailsViewModel @Inject constructor(
    private val indexerApis: IndexerApis
) : ViewModel() {
    var state by mutableStateOf(BlockDetailsUIState(loading = true))
        private set

    fun loadUI(blockID: String) {
        state = BlockDetailsUIState(loading = true)
        viewModelScope.launch {
            state = try {
                val block = indexerApis.getBlock(blockID = blockID)
                BlockDetailsUIState(
                    block = block
                )
            } catch (t: Throwable) {
                BlockDetailsUIState(
                    error = t.message
                )
            }
        }
    }
}

data class BlockDetailsUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val block: Block? = null,
)