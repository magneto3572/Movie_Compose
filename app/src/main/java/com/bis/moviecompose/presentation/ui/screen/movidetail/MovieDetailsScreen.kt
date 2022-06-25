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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun MovieDetailsScreen(
    poster: String,
    title: String,
    release: String,
    language : String,
    star : String,
    synopsis : String
) {
    val url = "https://image.tmdb.org/t/p/w342/$poster"
    Surface(modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row(verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.primary)
                    .padding(16.dp, 30.dp, 16.dp, 30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = title,
                        modifier = Modifier.size(140.dp, 230.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = title, color = colorScheme.onTertiary, fontSize = 18.sp)
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp)) {
                        Row{
                            Text(text = "Language :  ", color = colorScheme.onTertiary, fontSize = 12.sp)
                            Text(text = language, color = colorScheme.onTertiary, fontSize = 14.sp)
                        }

                        Row {
                            Text(text = "Star Rating ", color = colorScheme.onTertiary, fontSize = 12.sp)
                            Text(text = star, color = colorScheme.onTertiary, fontSize = 14.sp)
                        }

                        Row{
                            Text(text = "Release Date :  ", color = colorScheme.onTertiary, fontSize = 12.sp)
                            Text(text = release, color = colorScheme.onTertiary, fontSize = 14.sp)
                        }
                    }
                }
            }
            
            Column(modifier = Modifier.background(colorScheme.onBackground)
                .fillMaxSize()
                .padding(20.dp)) {
                Text(text = "Synopsis", color = colorScheme.tertiary, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp, 10.dp))
                Text(text = synopsis, color = colorScheme.tertiary, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun display(){
    MovieDetailsScreen( "","","", "", "", "")
}