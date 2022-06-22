package com.bis.moviecompose.presentation.ui.screen.movidetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage


@Composable
fun MovieDetailsScreen(
    navController: NavHostController?,
    poster: String,
    title: String,
    release: String
) {
    val url = "https://image.tmdb.org/t/p/w342/$poster"
    Surface(modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Row(verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.primary)
                    .padding(16.dp, 16.dp,16.dp, 30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = title,
                        modifier = Modifier.size(150.dp, 250.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = title, color = Color.White, fontSize = 16.sp )
                    Text(text = release, color = Color.White, fontSize = 12.sp)
                }
            }
            
            Row() {
                
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun display(){
    MovieDetailsScreen(null, "","","")
}