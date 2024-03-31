@file:OptIn(ExperimentalMaterial3Api::class)

package namada.crab_orange.namada_explorer.ui.transaction_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import namada.crab_orange.namada_explorer.extension.formatDate
import namada.crab_orange.namada_explorer.ui.components.DataUI
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun TransactionDetailsPage(
    txHash: String,
    navController: NavController,
    viewModel: TransactionDetailsViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    if (lifecycleState == Lifecycle.State.STARTED) {
        viewModel.loadUI(txHash = txHash)
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
                if (viewModel.state.loading)
                    item {
                        CircularProgressIndicator()
                    }
                else if (viewModel.state.error != null)
                    item {
                        Text(text = viewModel.state.error!!, color = Color.Red)
                    }
                else {
                    val transaction = viewModel.state.transaction ?: return@Screen
                    val block = viewModel.state.block ?: return@Screen

                    item {
                        DataUI(
                            title = "Tx Hash",
                            data = transaction.hash.uppercase(),
                        )
                    }
                    item {
                        DataUI(
                            title = "Type",
                            data = transaction.txType,
                        )
                    }
                    item {
                        DataUI(
                            title = "Status",
                            data = if (transaction.txType == "Wrapper" || transaction.returnCode == 0L)
                                "Success"
                            else
                                "Failed",
                        )
                    }
                    item {
                        DataUI(
                            title = "Block",
                            data = block.blockID.uppercase(),
                        ) {
                            navBackStackEntry?.savedStateHandle?.set(
                                "blockID",
                                block.blockID
                            )
                            navController.navigate("BlockDetails")
                        }
                    }
                    item {
                        DataUI(
                            title = "Time",
                            data = block.header.time.formatDate,
                        )
                    }
                    item {
                        DataUI(
                            title = "Network",
                            data = block.header.chainID,
                        )
                    }
                    item {
                        DataUI(
                            title = "Height",
                            data = "#${block.header.height}",
                        )
                    }
                }
            }
        }
    }
}