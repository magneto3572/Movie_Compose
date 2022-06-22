package com.bis.moviecompose.presentation.ui.screen.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bis.moviecompose.data.Resource
import com.bis.moviecompose.domain.utils.normalizedItemPosition
import com.example.assignment.data.Models.result
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlin.math.absoluteValue


val url = "https://image.tmdb.org/t/p/w342"
var page = 1

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel) {
    HomeMovieGrid(viewModel, navController)
}

@Composable
fun HomeMovieGrid(viewModel: HomeScreenViewModel, navController: NavHostController) {
    val list = remember{ mutableStateListOf<result>()}
    val loader by rememberUpdatedState(Loader())

    LaunchedEffect(Unit) {
        viewModel.getData(page)
        viewModel.movieRes.collect{
            when (it){
                is Resource.Success->{
                    list.addAll(it.value.results)
                }
                is Resource.Loading ->{
                    loader
                }
                is Resource.Failure ->{

                }
                else -> {}
            }
        }
    }
    setupLayout(list, viewModel, navController)
}

@Composable
fun setupLayout(
    list: SnapshotStateList<result>,
    viewModel: HomeScreenViewModel,
    navController: NavHostController
) {
    val lazyState = rememberLazyListState()

    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().padding(0.dp, 16.dp, 0.dp, 0.dp)){
            Text(text = "Movie", fontSize = 18.sp)
        }
        LazyRow(
            state = lazyState,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .height(200.dp)
        ){
            items(list, key = { it.id }) { item ->
                Child(
                    item,
                    modifier = Modifier
                        .size(250.dp, 180.dp)
                        .graphicsLayer {
                            val value =
                                1 - (lazyState.layoutInfo.normalizedItemPosition(item.id).absoluteValue * 0.05F)
                            alpha = value
                            scaleX = value
                            scaleY = value
                        },
                    imageModifier = Modifier.requiredWidth(260.dp)
                )
            }
        }
        androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
            columns = GridCells.Adaptive(125.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            itemsIndexed(list) { index, destination ->
                Row(Modifier.padding(5.dp)) {
                    if(index == list.lastIndex){
                        page++
                        viewModel.getData(page)
                    }
                    ItemLayout(destination, index, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Child(item: result, modifier: Modifier, imageModifier: Modifier,) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
    ) {
        Box{
            AsyncImage(
                model = url+item.poster_path,
                contentDescription = item.title,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.bis.moviecompose.R.raw.loading))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Center,
        composition = composition,
        progress = { progress
        })
}

@Composable
fun ItemLayout(destination: result, index: Int, navController: NavHostController) {
    destination.apply {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(2.dp, RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("details/$title/$release_date$poster_path")
                }
        ) {

            AsyncImage(
                model = url+poster_path,
                contentDescription = destination.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(125.dp, 180.dp),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}