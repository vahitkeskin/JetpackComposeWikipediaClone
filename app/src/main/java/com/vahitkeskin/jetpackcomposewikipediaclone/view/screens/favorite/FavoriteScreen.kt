package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.favorite

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.R.attr.ratingBarStyleSmall
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.load
import com.flaviofaria.kenburnsview.KenBurnsView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.vahitkeskin.jetpackcomposewikipediaclone.R
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaExpandableText
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.RatingBarColor
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.PagePreview
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.FavoriteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    var pagerItemState by remember { mutableStateOf(0) }
    viewModel.getFavorite()
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        pageCount = viewModel.favorite.size,
        initialOffscreenLimit = 2,
    )

    if (viewModel.favorite.isNotEmpty()) {

        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    animationSpec = tween(600)
                )
            }
        }

        Column(
            modifier = Modifier.padding(
                top = 30.dp, bottom = 40.dp
            )
        ) {
            HorizontalPager(
                state = pagerState, verticalAlignment = Alignment.Top
            ) { pageIndex ->
                val place = viewModel.favorite[pageIndex]
                Card(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(pageIndex).absoluteValue

                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                        }
                        .fillMaxWidth()
                        .padding(
                            start = 12.dp, end = 12.dp, bottom = 70.dp
                        ),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Box {
                        val customView = KenBurnsView(LocalContext.current).also { imageView ->
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            imageView.load(place.itemImage)//URL Selected -> place.url
                        }
                        AndroidView(
                            factory = { customView }, modifier = Modifier.fillMaxSize()
                        )

                        Column(
                            Modifier
                                .align(Alignment.BottomStart)
                                .background(Color(android.graphics.Color.parseColor("#80000000")))
                                .padding(16.dp)
                        ) {

                            Text(
                                text = place.itemTitle,
                                style = MaterialTheme.typography.h5,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            println("randomRating value: ${place.itemRandomRating}")
                            val ratingBar = RatingBar(
                                LocalContext.current, null, ratingBarStyleSmall
                            ).apply {
                                rating = place.itemRandomRating
                                progressDrawable.setColorFilter(
                                    ContextCompat.getColor(context, R.color.rating_bar_color),
                                    PorterDuff.Mode.SRC_ATOP
                                )
                            }

                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AndroidView(factory = { ratingBar })
                                Text(
                                    text = place.itemRandomRating.toString(), color = RatingBarColor
                                )
                            }
                            WikipediaExpandableText(
                                text = place.itemDetail, modifier = Modifier.padding(top = 8.dp)
                            )
                            pagerItemState = pageIndex
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            22.dp,
                        ), verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        //Delete
                        Row(modifier = Modifier.clickable {
                            Toast.makeText(
                                context,
                                "Click item title: ${place.itemTitle}",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.deleteFavorite(place)
                        }) {
                            Text(text = "Delete ")
                            Icon(
                                imageVector = Icons.Default.Delete, contentDescription = null
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Saved on: ${place.itemSaveTime}",
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
        //Delete All
        if (viewModel.favorite.size > 1) {
            Row(modifier = Modifier
                .clickable {
                    viewModel.deleteAllFavorite()
                }
                .fillMaxWidth()
                .padding(5.dp), horizontalArrangement = Arrangement.End) {
                Text(text = "Delete All ")
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = null
                    )
                    Text(text = "${viewModel.favorite.size}", color = Color.Black, fontSize = 12.sp)
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyAnimation(PagePreview.FAVORITE_SCREEN)
            TextShadow("Empty Favorites")
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    Surface(color = MaterialTheme.colors.background) {
        FavoriteScreen()
    }
}