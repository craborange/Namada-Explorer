package namada.crab_orange.namada_explorer.ui.validators

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import namada.crab_orange.namada_explorer.apis.StakePoolApis
import namada.crab_orange.namada_explorer.data.Validator
import javax.inject.Inject

@HiltViewModel
class ValidatorViewModel @Inject constructor(private val stakePoolApis: StakePoolApis) :
    ViewModel() {
    var state by mutableStateOf(ValidatorsUIState(loading = true))
        private set

    init {
        viewModelScope.launch {
            state = try {
                val validators = stakePoolApis.getValidator()
                ValidatorsUIState(
                    validtors = validators.validators
                )
            } catch (t: Throwable) {
                ValidatorsUIState(error = t.message)
            }
        }
    }
}

data class ValidatorsUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val validtors: List<Validator> = listOf(),
)