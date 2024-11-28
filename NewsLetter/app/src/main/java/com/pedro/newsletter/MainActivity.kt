package com.pedro.newsletter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pedro.newsletter.presentation.news_list.NewsListScreen
import com.pedro.newsletter.presentation.news_list.NewsListViewModel
import com.pedro.newsletter.ui.theme.NewsLetterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicando o tema da aplicação
            NewsLetterTheme {
                NewsAppearanceBox()
            }
        }
    }
}

@Composable
fun NewsAppearanceBox() {
    var selectedNewsURL by remember { mutableStateOf<String?>(null) }

    if (selectedNewsURL == null) {
        val newsListViewModel: NewsListViewModel = viewModel()
        NewsListScreen(viewModel = newsListViewModel) { newsURL ->
            selectedNewsURL = newsURL
        }
    } /*else {
        println("Welcome there ${selectedNewsURL}")

        val newsDetailListViewModel: NewsDetailListViewModel = viewModel()
        NewsDetailList(viewModel = articleDetailViewModel, articleId = selectedArticleId!!) {
            selectedArticleId = null
        }
    }*/
}