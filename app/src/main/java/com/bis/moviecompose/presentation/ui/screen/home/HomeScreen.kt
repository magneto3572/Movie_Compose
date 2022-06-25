package com.bis.moviecompose.presentation.ui.screen.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
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
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min


val url = "https://image.tmdb.org/t/p/w342"
var page = 1

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel) {
    HomeMovieGrid(viewModel, navController)
}

@Composable
fun HomeMovieGrid(viewModel: HomeScreenViewModel, navController: NavHostController) {
    val scrollState = rememberLazyGridState()
    val scrollOffset = derivedStateOf {
        min(1f, 1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))
    }
    val list = remember{ mutableStateListOf<result>()}
    val showProgressBarState = remember { mutableStateOf(false) }
    if (showProgressBarState.value) { Loader() }

    LaunchedEffect(Unit) {
        viewModel.getData(page)
        viewModel.movieRes.collect{
            when (it){
                is Resource.Success->{
                    showProgressBarState.value = false
                    list.addAll(it.value.results)
                }
                is Resource.Loading ->{
                    showProgressBarState.value = true
                }
                is Resource.Failure ->{
                    showProgressBarState.value = false
                }
                else -> {
                    showProgressBarState.value = false
                }
            }
        }
    }
    SetupLayout(list, viewModel, navController, scrollOffset, scrollState)
}

@Composable
fun ShowProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SetupLayout(
    list: SnapshotStateList<result>,
    viewModel: HomeScreenViewModel,
    navController: NavHostController,
    scrollOffset: State<Float>,
    scrollState: LazyGridState
) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {

        HoriRow(scrollOffset, list)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(125.dp),
            contentPadding = PaddingValues(8.dp),
            state = scrollState
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

@Composable
fun HoriRow(scrollOffset: State<Float>, list: SnapshotStateList<result>) {
    val lazyState = rememberLazyListState()
    val imageSize by animateDpAsState(targetValue = max(0.dp, 230.dp * scrollOffset.value.toFloat()))
    val value = max(0f, (1f * scrollOffset.value.toFloat()))
    var movietext = "Movie"

    if (imageSize <= 0.dp){
        movietext = "Now Showing"
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 12.dp)){
        Text(text = movietext, fontSize = 16.sp, color = Color.White)
    }


    Column(modifier = Modifier
        .graphicsLayer {
            alpha = value
        }
        .height(imageSize)
        .fillMaxWidth()) {
        LazyRow(
            state = lazyState,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ){
            items(list, key = { it.id }) { item ->
                Child(
                    item,
                    modifier = Modifier
                        .size(290.dp, 200.dp)
                        .graphicsLayer {
                            val value =
                                1 - (lazyState.layoutInfo.normalizedItemPosition(item.id).absoluteValue * 0.05F)
                            alpha = value
                            scaleX = value
                            scaleY = value
                        },
                    imageModifier = Modifier.requiredWidth(290.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Now Showing", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 4.dp))
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
        progress = { progress })
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
                    navController.navigate("details/$title/$release_date$poster_path/$original_language/${vote_average.toString()}/$overview")
                }
        ) {
            val showProgressBarState = remember { mutableStateOf(false) }
            if (showProgressBarState.value) { ShowProgressBar() }
            AsyncImage(
                onLoading = {
                    showProgressBarState.value = true
                },
                onSuccess = {
                    showProgressBarState.value = false
                },
                model = url+poster_path,
                alignment = Alignment.Center,
                contentDescription = destination.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.defaultMinSize(125.dp, 180.dp)
            )
        }
    }
}