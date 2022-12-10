package com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahitkeskin.jetpackcomposewikipediaclone.model.created.MainPageTagModel
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.MainPageRepository
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Utils
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.htmlBackgroundColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 3.12.2022
 */
@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val mainPageRepository : MainPageRepository
) : ViewModel() {

    private val _mainPageStateFlow = MutableLiveData<ArrayList<MainPageTagModel>>()
    val mainPageStateFlow: LiveData<ArrayList<MainPageTagModel>> = _mainPageStateFlow

    init {
        getMainPageData()
    }

    private fun getSampleResponse() = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(mainPageRepository))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }

    private fun getMainPageData() {
        viewModelScope.launch {
            getSampleResponse().collect {
                when (it) {
                    is State.DataState -> {
                        val htmlStar = it.data.invoke().parse.text.star
                        try {
                            _mainPageStateFlow.value = getMainPageTag(htmlStar)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            flow {
                                emit(Utils.resolveError(e))
                            }
                        }
                        State.DataState(getMainPageTag(htmlStar))
                    }
                    is State.ErrorState -> State.ErrorState(it.exception)
                    is State.LoadingState -> State.LoadingState
                }
            }
        }
    }

    private fun getMainPageTag(htmlStar: String) : ArrayList<MainPageTagModel> {
        val doc = Jsoup.parse(htmlStar)
        val mainPageTagModel = ArrayList<MainPageTagModel>()

        //TAG6
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select(Constants.TAG6_TITLE_AND_BACKGROUND).text(),
                detail = doc.select(Constants.TAG6_DETAIL).select(Constants.TR).get(2).text(),
                image = Constants.HTTPS + doc.select(Constants.TAG6_IMAGE).select(Constants.IMG).attr(Constants.SRC),
                background = doc.select(Constants.TAG6_TITLE_AND_BACKGROUND).html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG1
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select(Constants.TAG1_TAG6_TITLE_AND_BACKGROUND).text(),
                detail = doc.select(Constants.TAG1_DETAIL_AND_IMAGE).select(Constants.A).text(),
                image = Constants.HTTPS + doc.select(Constants.TAG1_DETAIL_AND_IMAGE).select(Constants.IMG).attr(Constants.SRC),
                background = doc.select(Constants.TAG1_BACKGROUND).html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG2
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select(Constants.TAG2_TITLE).text(),
                detail = doc.select(Constants.TAG2_DETAIL_AND_IMAGE).select(Constants.A).text(),
                image = Constants.HTTPS + doc.select(Constants.TAG2_DETAIL_AND_IMAGE).select(Constants.IMG).attr(Constants.SRC),
                background = doc.select(Constants.TAG2_BACKGROUND).html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG3
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select(Constants.TAG3_TITLE).text(),
                detail = doc.select(Constants.TAG3_DETAIL_AND_IMAGE).select(Constants.A).text(),
                image = Constants.HTTPS + doc.select(Constants.TAG3_DETAIL_AND_IMAGE).select(Constants.IMG).attr(Constants.SRC),
                background = doc.select(Constants.TAG1_TAG6_TITLE_AND_BACKGROUND).html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG4
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select(Constants.TAG4_TAG5_TITLE).get(0).text(),
                detail = doc.select(Constants.TAG4_TAG5_DETAIL_IMAGE).get(0).select(Constants.A).text(),
                image = Constants.HTTPS + doc.select(Constants.TAG4_TAG5_DETAIL_IMAGE).get(0).select(Constants.IMG).attr(Constants.SRC),
                background = doc.select(Constants.TAG4_TAG5_BACKGROUND).html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG5
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select(Constants.TAG4_TAG5_TITLE).get(1).text(),
                detail = doc.select(Constants.TAG5_DETAIL).get(1).select(Constants.A).text(),
                image = Constants.HTTPS + doc.select(Constants.TAG4_TAG5_DETAIL_IMAGE).get(1).select(Constants.IMG).attr(Constants.SRC),
                background = doc.select(Constants.TAG4_TAG5_BACKGROUND).get(1).html().trimIndent().htmlBackgroundColor()
            )
        )

        return mainPageTagModel
    }

}