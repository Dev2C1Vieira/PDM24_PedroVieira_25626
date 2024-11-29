package com.pedro.newsletter.presentation.news_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pedro.newsletter.presentation.shared.SharedViewModel

@Composable
fun NewsDetailListScreen(
    viewModel: NewsDetailListViewModel,
    newsURL: String,
    sharedViewModel: SharedViewModel, // Recebendo o ViewModel compartilhado
    onBack: () -> Unit
) {
    val selectedCategory = sharedViewModel.selectedCategory // Acessando a categoria atual

    LaunchedEffect(newsURL) {
        viewModel.fetchNewsDetail(sharedViewModel.selectedCategory, newsURL)
    }

    val newsDetail = viewModel.newsDetail.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        newsDetail.value?.let { news ->

            // Imagem da notícia com bordas arredondadas
            val painter = rememberAsyncImagePainter(model = news.image_url)
            Image(
                painter = painter,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp)), // Adicionando bordas arredondadas
                contentScale = ContentScale.Crop
            )

            // Título da notícia com maior destaque
            Text(
                text = news.title,
                style = TextStyle(
                    fontSize = 26.sp, // Aumentando o tamanho da fonte para destacar
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally) // Centralizando o título
            )

            // Informações da notícia: Data, autor e atualizações
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Category: $selectedCategory", // Exibindo a categoria na tela de detalhes
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Created On ${news.createdDate}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Published On ${news.publishedDate}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Last Updated On ${news.updatedDate}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Created By ${news.author}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Resumo da notícia
            BasicText(
                text = news.summary,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .fillMaxWidth()
            )

            // Botão de voltar estilizado
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Back", fontSize = 18.sp)
            }
        } ?: run {
            // Mensagem de carregamento com estilo
            Text("Loading details...", fontSize = 18.sp, color = Color.Gray)
        }
    }
}