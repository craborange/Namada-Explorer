package namada.crab_orange.namada_explorer.ui.validators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import namada.crab_orange.namada_explorer.extension.formatLongToText
import namada.crab_orange.namada_explorer.extension.middleEllipsis
import namada.crab_orange.namada_explorer.extension.roundOffDecimal
import namada.crab_orange.namada_explorer.ui.components.Item
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun ValidatorsScreen(
    navController: NavController,
    viewModel: ValidatorViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Screen {
        if (viewModel.state.loading)
            item {
                CircularProgressIndicator()
            }
        else if (viewModel.state.error != null)
            item {
                Text(text = viewModel.state.error!!, color = Color.Red)
            }
        else
            items(viewModel.state.validtors) { validator ->
                Item(
                    onClick = {
                        navBackStackEntry?.savedStateHandle?.set(
                            "validator",
                            validator
                        )
                        navController.navigate("ValidatorDetails")
                    }
                ) {
                    if (validator.moniker != null) {
                        if(validator.moniker.isBlank() && validator.operatorAddress != null) {
                            Text(text = validator.operatorAddress.middleEllipsis, fontWeight = FontWeight.Bold)
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(0.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(text = validator.moniker, fontWeight = FontWeight.Bold)

                                if(validator.operatorAddress != null) {
                                    Text(text = " - ")

                                    Text(
                                        text = validator.operatorAddress.middleEllipsis,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    } else {
                        if(validator.operatorAddress != null) {
                            Text(text = validator.operatorAddress.middleEllipsis, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
    }
}