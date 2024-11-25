package com.pedro.newsletter.presentation.news_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.ui.theme.NewsLetterTheme
import androidx.compose.material3.MaterialTheme

@Composable
fun NewsListScreen(viewModel: NewsListViewModel = viewModel()) {

    // Chama a função para buscar as notícias da seção "home"
    LaunchedEffect(Unit) {
        val section = "home"  // Seção desejada
        val apiKey = "your-api-key" // Substitua pela sua chave de API
        viewModel.fetchNews(section, apiKey)
    }

    // Observa a lista de notícias
    val newsList = viewModel.newsList.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    // Layout da tela
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading == true) {
            // Exibe um carregamento enquanto as notícias são buscadas
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center) // Usando Box e o align dentro dele
            )
        } else if (errorMessage != null) {
            // Exibe uma mensagem de erro
            Text("Erro: $errorMessage", modifier = Modifier.align(Alignment.Center))
        } else {
            // Exibe as notícias na lista
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                items(newsList) { news ->
                    NewsListItem(news = news)
                }
            }
        }
    }
}


@Composable
fun NewsListItem(news: News) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = news.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.summary, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Autor: ${news.author}", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsListScreen() {
    NewsLetterTheme {
        NewsListScreen()
    }
}
