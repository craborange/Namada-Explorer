package namada.crab_orange.namada_explorer.ui.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import namada.crab_orange.namada_explorer.extension.middleEllipsis
import namada.crab_orange.namada_explorer.extension.timeAgoString
import namada.crab_orange.namada_explorer.ui.components.Item
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun BlocksScreen(
    navController: NavController,
    viewModel: BlocksViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val paging = viewModel.paging.collectAsLazyPagingItems()

    Screen {
        if (paging.itemCount == 0) {
            if (viewModel.state.loading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            } else if (viewModel.state.error != null) {
                item {
                    Text(text = viewModel.state.error!!, color = Color.Red)
                }
            }
        } else {
            items(paging.itemCount) { index ->
                val block = paging[index] ?: return@items
                Item(
                    onClick = {
                        navBackStackEntry?.savedStateHandle?.set(
                            "blockID",
                            block.blockID
                        )
                        navController.navigate("BlockDetails")
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "#${block.header.height}", fontWeight = FontWeight.Bold)

                        Text(text = block.header.time.timeAgoString)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = block.blockID.middleEllipsis, fontSize = 12.sp)

                        Text(text = block.header.proposerAddress.middleEllipsis, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}