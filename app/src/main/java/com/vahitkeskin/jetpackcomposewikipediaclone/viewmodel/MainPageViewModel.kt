package com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahitkeskin.jetpackcomposewikipediaclone.model.created.MainPageTagModel
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.MainPageRepository
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
                title = doc.select("th#mp-dyk-head").text(),
                detail = doc.select("div#mf-dyk").select("tr").get(2).text(),
                image = "https:" + doc.select("td#mp-dyk").select("img").attr("src"),
                background = doc.select("th#mp-dyk-head").html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG1
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select("th#mp-tfa-head").text(),
                detail = doc.select("td#mp-tfa").select("a").text(),
                image = "https:" + doc.select("td#mp-tfa").select("img").attr("src"),
                background = doc.select("th#mp-bm-head").html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG2
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select("div#mp-tfp-h2").text(),
                detail = doc.select("td#mp-tfp").select("a").text(),
                image = "https:" + doc.select("td#mp-tfp").select("img").attr("src"),
                background = doc.select("th#mp-tfp-head").html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG3
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select("div#mp-bm-h2").text(),
                detail = doc.select("td#mp-bm").select("a").text(),
                image = "https:" + doc.select("td#mp-bm").select("img").attr("src"),
                background = doc.select("th#mp-tfa-head").html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG4
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select("div#mp-itn-h2").get(0).text(),
                detail = doc.select("td#mp-itn").get(0).select("a").text(),
                image = "https:" + doc.select("td#mp-itn").get(0).select("img").attr("src"),
                background = doc.select("th#mp-itn-head").html().trimIndent().htmlBackgroundColor()
            )
        )

        //TAG5
        mainPageTagModel.add(
            MainPageTagModel(
                title = doc.select("div#mp-itn-h2").get(1).text(),
                detail = doc.select("div.hlist-separated").get(1).select("a").text(),
                image = "https:" + doc.select("td#mp-itn").get(1).select("img").attr("src"),
                background = doc.select("th#mp-itn-head").get(1).html().trimIndent().htmlBackgroundColor()
            )
        )

        return mainPageTagModel
    }

}