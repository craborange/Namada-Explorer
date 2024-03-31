package namada.crab_orange.namada_explorer.ui.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import namada.crab_orange.namada_explorer.apis.IndexerApis
import namada.crab_orange.namada_explorer.data.Block
import namada.crab_orange.namada_explorer.data.Transaction
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val indexerApis: IndexerApis
) : ViewModel() {
    var state by mutableStateOf(TransactionsUI(loading = true))
        private set

    val paging = Pager(
        initialKey = 1,
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = {
            object : PagingSource<Int, Transaction>() {
                override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                    }
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
                    return withContext(Dispatchers.IO) {
                        try {
                            val page = params.key ?: 1
                            state = state.copy(loading = true, error = null)
                            val responses = indexerApis.getTransaction(page = page, pageSize = 15)

                            for (transaction in responses.data) {
                                val elementBlock = state.blocksMap[transaction.blockID]
                                if (elementBlock == null) {
                                    state.blocksMap[transaction.blockID] =
                                        indexerApis.getBlock(blockID = transaction.blockID)
                                }
                            }
                            LoadResult.Page(
                                data = responses.data,
                                prevKey = if (page == 1) null else page - 1,
                                nextKey = if (responses.total <= responses.data.size) null else page + 1
                            )
                        } catch (e: Exception) {
                            state = TransactionsUI(error = e.message)
                            LoadResult.Error(e)
                        }
                    }
                }
            }
        }
    ).flow
}

data class TransactionsUI(
    val loading: Boolean = false,
    val error: String? = null,
    var blocksMap: MutableMap<String, Block> = mutableMapOf()
)