package namada.crab_orange.namada_explorer.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import namada.crab_orange.namada_explorer.apis.IndexerApis
import namada.crab_orange.namada_explorer.apis.StakePoolApis
import namada.crab_orange.namada_explorer.extension.formatDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val indexerApis: IndexerApis,
    private val stakePoolApis: StakePoolApis
) : ViewModel() {
    var state by mutableStateOf(HomeUIState(loading = true))
        private set

    init {
        viewModelScope.launch {
            state = try {
                val blocks = indexerApis.getBlock(1, 1)
                val validators = stakePoolApis.getValidator()

                val latestBlock = blocks.data.first()

                val blockSize = blocks.total

                HomeUIState(
                    network = latestBlock.header.chainID,
                    blockSize = blockSize,
                    latestBlockTime = latestBlock.header.time.formatDate,
                    validatorCount = validators.validators.size,
                )
            } catch (t: Throwable) {
                HomeUIState(error = t.message)
            }
        }
    }
}

data class HomeUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val network: String = "",
    val blockSize: Int = 0,
    val latestBlockTime: String = "",
    val validatorCount: Int = 0,
)