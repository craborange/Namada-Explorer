@file:OptIn(ExperimentalMaterial3Api::class)

package namada.crab_orange.namada_explorer.ui.block_details

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
import namada.crab_orange.namada_explorer.extension.formatDate
import namada.crab_orange.namada_explorer.extension.middleEllipsis
import namada.crab_orange.namada_explorer.extension.timeAgoString
import namada.crab_orange.namada_explorer.ui.components.DataUI
import namada.crab_orange.namada_explorer.ui.components.Item
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun BlockDetailsPage(
    blockID: String,
    navController: NavController,
    viewModel: BlockDetailsViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    if (lifecycleState == Lifecycle.State.STARTED) {
        viewModel.loadUI(blockID = blockID)
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
                if (viewModel.state.loading) {
                    item {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.state.error != null) {
                    item {
                        Text(text = viewModel.state.error!!, color = Color.Red)
                    }
                } else {
                    val block = viewModel.state.block ?: return@Screen

                    item {
                        DataUI(
                            title = "Block ID",
                            data = block.blockID.uppercase(),
                        )
                    }
                    item {
                        DataUI(
                            title = "Proposer address",
                            data = block.header.proposerAddress.uppercase(),
                        )
                    }
                    item {
                        DataUI(
                            title = "Time",
                            data = "${block.header.time.formatDate} (${block.header.time.timeAgoString})",
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
                    item {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                text = "Transaction of blocks",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )

                            Text(
                                text = " (${block.txHashes.size})",
                                fontSize = 16.sp,
                            )
                        }
                    }

                    items(block.txHashes) { txHash ->
                        Item(
                            onClick = {
                                navBackStackEntry?.savedStateHandle?.set(
                                    "transaction_hash",
                                    txHash.hashID
                                )
                                navController.navigate("TransactionDetails")
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = txHash.hashID.middleEllipsis,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = txHash.txType)
                            }
                        }
                    }
                }
            }
        }
    }
}