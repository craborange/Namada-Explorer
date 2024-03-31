package namada.crab_orange.namada_explorer.ui.transaction_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import namada.crab_orange.namada_explorer.apis.IndexerApis
import namada.crab_orange.namada_explorer.data.Block
import namada.crab_orange.namada_explorer.data.Transaction
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val indexerApis: IndexerApis
) : ViewModel() {
    var state by mutableStateOf(TransactionsDetailsUIState(loading = true))
        private set

    fun loadUI(txHash: String) {
        state = TransactionsDetailsUIState(loading = true)
        viewModelScope.launch {
            state = try {
                val transaction = indexerApis.getTransaction(txHash = txHash)
                val block = indexerApis.getBlock(blockID = transaction.blockID)
                TransactionsDetailsUIState(
                    transaction = transaction,
                    block = block
                )
            } catch (t: Throwable) {
                TransactionsDetailsUIState(
                    error = t.message
                )
            }
        }
    }
}

data class TransactionsDetailsUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val transaction: Transaction? = null,
    val block: Block? = null,
)