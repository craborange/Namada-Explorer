@file:OptIn(ExperimentalMaterial3Api::class)

package namada.crab_orange.namada_explorer.ui.validator_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import namada.crab_orange.namada_explorer.data.Validator
import namada.crab_orange.namada_explorer.extension.formatLongToText
import namada.crab_orange.namada_explorer.extension.formatWithCommas
import namada.crab_orange.namada_explorer.extension.roundOffDecimal
import namada.crab_orange.namada_explorer.ui.components.DataUI
import namada.crab_orange.namada_explorer.ui.components.Item
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun ValidatorDetailsPage(
    validator: Validator,
    navController: NavController,
    viewModel: ValidatorDetailsViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    if (lifecycleState == Lifecycle.State.STARTED) {
        viewModel.loadUI(address = validator.address)
    }

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
                if (validator.moniker.isNotBlank()) {
                    item {
                        DataUI(
                            title = "Author",
                            data = validator.moniker,
                        )
                    }
                }
                item {
                    DataUI(
                        title = "Address",
                        data = validator.address,
                    )
                }
                item {
                    DataUI(
                        title = "Public key",
                        data = validator.pubKey.value.uppercase(),
                    )
                }
                item {
                    DataUI(
                        title = "Address",
                        data = "${validator.votingPower.formatLongToText} (${validator.votingPercentage.roundOffDecimal}%)",
                    )
                }
                if (viewModel.state.loading) {
                    item {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                text = "Signature block of validator",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                        }
                    }

                    item {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.state.error != null) {
                    item {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                text = "Signature block of validator",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                        }
                    }

                    item {
                        Text(text = viewModel.state.error!!, color = Color.Red)
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                text = "${viewModel.state.signatures.size} Signature block of validator",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                        }
                    }

                    items(viewModel.state.signatures) { signature ->
                        Item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = signature.blockNumber.formatWithCommas,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(text = "Status: ${signature.signStatus}")
                            }
                        }
                    }
                }
            }
        }
    }
}