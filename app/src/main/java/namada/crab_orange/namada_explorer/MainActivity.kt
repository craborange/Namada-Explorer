package namada.crab_orange.namada_explorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import namada.crab_orange.namada_explorer.data.Validator
import namada.crab_orange.namada_explorer.ui.MainScreen
import namada.crab_orange.namada_explorer.ui.block_details.BlockDetailsPage
import namada.crab_orange.namada_explorer.ui.transaction_details.TransactionDetailsPage
import namada.crab_orange.namada_explorer.ui.validator_details.ValidatorDetailsPage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "Main"
            ) {
                composable(route = "Main") {
                    MainScreen(
                        navController = navController
                    )
                }

                composable(route = "ValidatorDetails") {
                    val validator =
                        navController.previousBackStackEntry?.savedStateHandle?.get<Validator>(
                            "validator"
                        )
                            ?: return@composable
                    ValidatorDetailsPage(
                        navController = navController,
                        validator = validator
                    )
                }

                composable(route = "BlockDetails") {
                    val blockID =
                        navController.previousBackStackEntry?.savedStateHandle?.get<String>("blockID")
                            ?: return@composable
                    BlockDetailsPage(
                        blockID = blockID,
                        navController = navController
                    )
                }

                composable(route = "TransactionDetails") {
                    val transactionHash =
                        navController.previousBackStackEntry?.savedStateHandle?.get<String>("transaction_hash")
                            ?: return@composable
                    TransactionDetailsPage(
                        txHash = transactionHash,
                        navController = navController,
                    )
                }
            }
        }
    }
}