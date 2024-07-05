package com.loc.newsapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.loc.newsapp.presentation.Dimens.MediumPadding2
import com.loc.newsapp.presentation.Dimens.PageIndicatorWidth
import com.loc.newsapp.presentation.common.NewsButton
import com.loc.newsapp.presentation.common.NewsTextButton
import com.loc.newsapp.presentation.onboarding.components.OnBoardingPage
import com.loc.newsapp.presentation.onboarding.components.PageIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        /*Without remember, every recomposition would reset the pager to its initial state, which is not the desired behavior in most cases.*/
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get started")
                    else -> listOf("", "")
                }
            }
        }
        /*The pagerState contains information about the current page, the total number of pages, and any other relevant state needed to manage the pager's behavior.
        * index This is the lambda function parameter that represents the index of the current page being displayed. For each page, the HorizontalPager will invoke this lambda with the appropriate index, allowing you to specify what content should be displayed for that page.*/
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pages[index])
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                /* rememberCoroutineScope is a composable function provided by Jetpack Compose that returns a CoroutineScope bound to the composition. This scope is used to launch coroutines that will be automatically cancelled if the composable leaves the composition, ensuring lifecycle-aware coroutine management.
                * This is crucial in the context of Jetpack Compose, where composables can be recomposed frequently. If you were to use a regular CoroutineScope, you'd risk leaking coroutines or having them run even after the composable is no longer in the composition.*/
                val scope = rememberCoroutineScope()

                if (buttonState.value[0].isNotEmpty()) {
                    NewsTextButton(
                        text = buttonState.value[0],
                        onClick = {
                            /*Coroutine Launch: Inside the onClick lambda, scope.launch is used to launch a coroutine. This ensures that the code inside runs asynchronously and can perform operations like animations without blocking the main thread.
                            Coroutine Usage: By using a coroutine, you can safely and smoothly animate the page scroll without freezing the UI.*/
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                }

                NewsButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                event(OnBoardingEvent.SaveAppEntry)
                            } else {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}