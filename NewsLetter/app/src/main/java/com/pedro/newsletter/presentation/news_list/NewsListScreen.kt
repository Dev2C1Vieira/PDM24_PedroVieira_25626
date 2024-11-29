package com.pedro.newsletter.presentation.news_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.presentation.shared.SharedViewModel

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    sharedViewModel: SharedViewModel,
    onNewsSelected: (String) -> Unit
) {
    val news by viewModel.news.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // Pegando o estado de carregamento
    val selectedCategory = sharedViewModel.selectedCategory // Observando a categoria selecionada

    // Aciona a chamada à API sempre que a categoria mudar
    LaunchedEffect(selectedCategory) {
        viewModel.fetchNews(selectedCategory) // Chama a função da ViewModel passando a categoria selecionada
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Navbar
        Navbar(selectedCategory) { category ->
            // Atualiza a categoria selecionada no SharedViewModel
            sharedViewModel.selectedCategory = category
        }

        // Corpo da tela com as notícias
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (news.isEmpty()) {
            Text("No news available", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(news) { newsItem ->
                    NewsListItem(news = newsItem, onClick = { onNewsSelected(newsItem.url) })
                }
            }
        }
    }
}


@Composable
fun Navbar(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val categories = listOf("Geral", "Artes", "Automóveis", "Negócios", "Viagens")

        categories.forEach { category ->
            var isHovered by remember { mutableStateOf(false) }

            Text(
                text = category,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                ),
                color = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        // Atualiza a categoria selecionada
                        onCategorySelected(category)
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isHovered = true
                                tryAwaitRelease()
                                isHovered = false
                            }
                        )
                    }
                    .then(if (isHovered) Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)) else Modifier)
                    .padding(vertical = 8.dp)
            )
        }
    }
}


@Composable
fun NewsListItem(news: News, onClick: () -> Unit) {
    val imageBox = rememberAsyncImagePainter(
        model = news.image_url
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {

                Image(
                    painter = imageBox,
                    contentDescription = "News Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = news.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { onClick( ) }
                            .padding(end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = news.summary,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}