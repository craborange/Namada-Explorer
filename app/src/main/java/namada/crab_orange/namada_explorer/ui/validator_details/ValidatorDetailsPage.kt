@file:OptIn(ExperimentalMaterial3Api::class)

package namada.crab_orange.namada_explorer.ui.validator_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import namada.crab_orange.namada_explorer.data.Validator
import namada.crab_orange.namada_explorer.ui.components.DataUI
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun ValidatorDetailsPage(
    validator: Validator,
    navController: NavController,
    viewModel: ValidatorDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Text(
                        text = "Transaction details",
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Screen {
                if (validator.moniker != null) {
                    if (validator.moniker.isNotBlank()) {
                        item {
                            DataUI(
                                title = "Author",
                                data = validator.moniker,
                            )
                        }
                    }
                }
                if (validator.operatorAddress != null) {
                    item {
                        DataUI(
                            title = "Address",
                            data = validator.operatorAddress.uppercase(),
                        )
                    }
                }
                item {
                    DataUI(
                        title = "Hex address",
                        data = validator.hexAddress.uppercase(),
                    )
                }
            }
        }
    }
}