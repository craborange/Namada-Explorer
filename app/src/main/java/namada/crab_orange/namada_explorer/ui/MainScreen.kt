@file:OptIn(ExperimentalMaterial3Api::class)

package namada.crab_orange.namada_explorer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import namada.crab_orange.namada_explorer.R
import namada.crab_orange.namada_explorer.ui.blocks.BlocksScreen
import namada.crab_orange.namada_explorer.ui.home.HomeScreen
import namada.crab_orange.namada_explorer.ui.transactions.TransactionsScreen
import namada.crab_orange.namada_explorer.ui.validators.ValidatorsScreen

enum class MainScreenState(val image: Int) {
    Home(R.drawable.home),
    Validators(R.drawable.shield),
    Blocks(R.drawable.cube),
    Transactions(R.drawable.earth)
}

@Composable
fun MainScreen(
    navController: NavController,
) {
    var currentTab by rememberSaveable {
        mutableStateOf(MainScreenState.Home)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = currentTab.name,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = if (currentTab == MainScreenState.Home) R.drawable.logo else currentTab.image),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            TabRow(
                selectedTabIndex = currentTab.ordinal,
                modifier = Modifier.height(52.dp)
            ) {
                MainScreenState.entries.forEach { state ->
                    Tab(
                        icon = {
                            Icon(
                                painter = painterResource(id = state.image),
                                contentDescription = "",
                                tint = if (state == currentTab) Color.Black else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        text = {
                            Text(
                                text = state.name,
                                fontSize = 9.sp
                            )
                        },
                        selected = currentTab == state,
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            currentTab = state
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (currentTab) {
                MainScreenState.Home -> HomeScreen(
                    navController = navController,
                    blocksOnClick = {
                        currentTab = MainScreenState.Blocks
                    }, validatorsOnClick = {
                        currentTab = MainScreenState.Validators
                    }
                )

                MainScreenState.Validators -> ValidatorsScreen(navController = navController)
                MainScreenState.Blocks -> BlocksScreen(navController = navController)
                MainScreenState.Transactions -> TransactionsScreen(navController = navController)
            }
        }
    }
}