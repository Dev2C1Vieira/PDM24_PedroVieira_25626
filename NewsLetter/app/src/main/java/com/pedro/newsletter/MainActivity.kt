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
import com.pedro.newsletter.presentation.news_detail.NewsDetailListScreen
import com.pedro.newsletter.presentation.news_detail.NewsDetailListViewModel
import com.pedro.newsletter.presentation.news_list.NewsListScreen
import com.pedro.newsletter.presentation.news_list.NewsListViewModel
import com.pedro.newsletter.presentation.shared.SharedViewModel
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
    // Instanciando o SharedViewModel
    val sharedViewModel: SharedViewModel = viewModel()

    var selectedNewsURL by remember { mutableStateOf<String?>(null) }

    if (selectedNewsURL == null) {
        // Passando o SharedViewModel para NewsListScreen
        val newsListViewModel: NewsListViewModel = viewModel()
        NewsListScreen(
            viewModel = newsListViewModel,
            sharedViewModel = sharedViewModel, // Passando o SharedViewModel
            onNewsSelected = { newsURL ->
                selectedNewsURL = newsURL
            }
        )
    } else {
        // Passando o SharedViewModel para NewsDetailListScreen
        val newsDetailListViewModel: NewsDetailListViewModel = viewModel()
        NewsDetailListScreen(
            viewModel = newsDetailListViewModel,
            newsURL = selectedNewsURL!!,
            sharedViewModel = sharedViewModel, // Passando o SharedViewModel
            onBack = {
                selectedNewsURL = null
            }
        )
    }
}