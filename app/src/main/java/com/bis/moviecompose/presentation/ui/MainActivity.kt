package com.bis.moviecompose.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bis.moviecompose.presentation.ui.screen.home.HomeScreen
import com.bis.moviecompose.presentation.ui.screen.home.HomeScreenViewModel
import com.bis.moviecompose.presentation.ui.screen.movidetail.MovieDetailsScreen
import com.bis.moviecompose.presentation.ui.theme.MovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    LazyVerticalGridActivityScreen()
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun LazyVerticalGridActivityScreen(destinationViewModel: HomeScreenViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, destinationViewModel)
        }
        composable(route = "details/{title}/{release_date}/{poster_path}/{lan}/{star}/{syn}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("release_date") {
                    type = NavType.StringType
                },
                navArgument("poster_path") {
                    type = NavType.StringType
                },
                navArgument("lan") {
                    type = NavType.StringType
                },
                navArgument("star") {
                    type = NavType.StringType
                },
                navArgument("syn") {
                    type = NavType.StringType
                },
            )
        ) {
            val title = it.arguments?.getString("title")!!
            val release = it.arguments?.getString("release_date")!!
            val poster = it.arguments?.getString("poster_path")!!
            val language = it.arguments?.getString("lan")!!
            val star = it.arguments?.getString("star")!!
            val synopsis = it.arguments?.getString("syn")!!

            Log.d("LogPoster", poster.toString())

            MovieDetailsScreen(
                poster,
                title,
                release,
                language,
                star,
                synopsis
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieComposeTheme {
//        HomeScreen()
    }
}