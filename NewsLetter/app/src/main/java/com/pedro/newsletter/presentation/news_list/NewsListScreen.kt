package com.pedro.newsletter.presentation.news_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pedro.newsletter.domain.model.News

@Composable
fun NewsListScreen(viewModel: NewsListViewModel, onNewsSelected: (String) -> Unit) {

    val news by viewModel.news.collectAsState()

    if (news.isEmpty()) {
        Text("Loading news...")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(news) { news ->
                NewsListItem(news = news, onClick = { onNewsSelected(news.url) })
            }
        }
    }
}

@Composable
fun NewsListItem(news: News, onClick: () -> Unit) {
    val imageBox = rememberAsyncImagePainter(
        model = news.image_url
    )

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Image(
                painter = imageBox,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Text(text = news.title, style = MaterialTheme.typography.titleLarge)
            Text(text = news.summary, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onClick) {
                Text("See more")
            }
        }
    }
}
