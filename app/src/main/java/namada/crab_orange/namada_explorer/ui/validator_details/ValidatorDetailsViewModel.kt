package namada.crab_orange.namada_explorer.ui.validator_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import namada.crab_orange.namada_explorer.apis.StakePoolApis
import javax.inject.Inject

@HiltViewModel
class ValidatorDetailsViewModel @Inject constructor(
    private val stakePoolApis: StakePoolApis
) : ViewModel() {
    var state by mutableStateOf(ValidatorDetailsUIState(loading = true))
        private set

    fun loadUI(address: String) {
//        state = ValidatorDetailsUIState(loading = true)
//        viewModelScope.launch {
//            state = try {
//                val signatures = stakePoolApis.getSignature(address = address)
//                ValidatorDetailsUIState(
//                    signatures = signatures
//                )
//            } catch (t: Throwable) {
//                ValidatorDetailsUIState(
//                    error = t.message
//                )
//            }
//        }
    }
}

data class ValidatorDetailsUIState(
    val loading: Boolean = false,
    val error: String? = null,
)