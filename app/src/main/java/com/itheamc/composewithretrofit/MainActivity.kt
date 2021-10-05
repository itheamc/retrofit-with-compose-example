package com.itheamc.composewithretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itheamc.composewithretrofit.model.PostItem
import com.itheamc.composewithretrofit.ui.theme.ComposeWithRetrofitTheme
import com.itheamc.composewithretrofit.viewmodel.PostViewModel
import com.itheamc.composewithretrofit.viewmodel.PostViewModelFactory
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (application as PostApplication).postRepository
        val viewModel: PostViewModel by viewModels(factoryProducer = { PostViewModelFactory(repository) })

        setContent {
            ComposeWithRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ColumnLayout(viewModel = viewModel)
                }
            }
        }

    }
}

@ExperimentalMaterialApi
@Composable
fun ColumnLayout(viewModel: PostViewModel) {
    val scope = rememberCoroutineScope()
    val posts: ArrayList<PostItem> by viewModel.posts.observeAsState(arrayListOf())
    val loading: Boolean by viewModel.loading.observeAsState(true)
    val lazyListState = rememberLazyListState()

    // If loading
    if (loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Loading", style = MaterialTheme.typography.overline, fontFamily = FontFamily.Monospace)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            items(posts) { post ->
                ListItem(
                    modifier = Modifier.clickable { scope.launch {
                        viewModel.fetchPosts()
                    } },
                    text = {
                        Text(text = post.title)
                    },
                    secondaryText = {
                        Text(text = post.body, maxLines = 3, overflow = TextOverflow.Ellipsis)
                    },
                    overlineText = {
                        Text(text = post.id.toString())
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.ListAlt, contentDescription = null)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeWithRetrofitTheme {
        
    }
}