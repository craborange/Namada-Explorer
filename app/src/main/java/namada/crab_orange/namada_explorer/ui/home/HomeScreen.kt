package namada.crab_orange.namada_explorer.ui.home

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import namada.crab_orange.namada_explorer.R
import namada.crab_orange.namada_explorer.extension.formatLongToText
import namada.crab_orange.namada_explorer.ui.components.DataUI
import namada.crab_orange.namada_explorer.ui.components.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    blocksOnClick: () -> Unit,
    validatorsOnClick: () -> Unit
) {
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
            item {
                DataUI(
                    image = R.drawable.network,
                    title = "Network",
                    data = viewModel.state.network,
                )
            }
            item {
                DataUI(
                    image = R.drawable.clock,
                    title = "Time",
                    data = viewModel.state.latestBlockTime,
                )
            }
            item {
                DataUI(
                    image = R.drawable.users,
                    title = "Validator",
                    data = viewModel.state.validatorCount.toString(),
                ) {
                    validatorsOnClick()
                }
            }
            item {
                DataUI(
                    image = R.drawable.cube,
                    title = "Blocks",
                    data = viewModel.state.blockSize.toString(),
                ) {
                    blocksOnClick()
                }
            }
        }
    }
}