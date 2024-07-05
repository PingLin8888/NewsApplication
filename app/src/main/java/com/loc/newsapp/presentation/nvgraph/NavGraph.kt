package com.loc.newsapp.presentation.nvgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.loc.newsapp.presentation.onboarding.OnBoardingScreen
import com.loc.newsapp.presentation.onboarding.OnBoardingViewModel

/*defines a navigation graph using Jetpack Compose and Hilt for dependency injection.
It sets up navigation for an Android application, specifically handling the onboarding flow
with a ViewModel.*/
@Composable
/*This function takes startDestination as a parameter, which defines the initial screen to be displayed.*/
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    /*Sets up the navigation host for the app, which manages the navigation state and back stack.*/
    NavHost(navController = navController, startDestination = startDestination){
        /*Defines a nested navigation graph with a specific route and startDestination.*/
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            /*Defines a composable destination within the navigation graph.
            This destination is associated with the onboarding screen.*/
            composable(
                route = Route.OnBoardingScreen.route
            ){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                /*Renders the OnBoardingScreen composable and passes the onEvent function
                from the OnBoardingViewModel to handle events.*/
                OnBoardingScreen(
                    event =viewModel::onEvent
//                            event = {
//                            viewModel.onEvent(it)
//                        }
                )
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigationScreen.route
        ){
            composable(route = Route.NewsNavigationScreen.route){
                Text(text = "News navigator screen")
            }
        }
    }
}