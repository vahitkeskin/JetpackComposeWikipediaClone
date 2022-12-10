package com.vahitkeskin.jetpackcomposewikipediaclone.utils

class Constants {
    companion object {

        //NOTE
        //https://towardsdev.com/using-android-jetpack-datastore-with-jetpack-compose-6184338cf9c0

        const val BASE_URL = "https://tr.wikipedia.org"

        const val FAVORITE_SCREEN_TABLE_NAME = "favoriteScreen"
        const val LAST_SEARCH_TABLE_NAME = "lastSearch"

        const val DEFAULT_MINIMUM_TEXT_LINE = 3

        //Main Page TAGS
        const val TAG1_TAG6_TITLE_AND_BACKGROUND = "th#mp-tfa-head"
        const val TAG1_DETAIL_AND_IMAGE = "td#mp-tfa"
        const val TAG1_BACKGROUND = "th#mp-bm-head"

        const val TAG2_DETAIL_AND_IMAGE = "td#mp-tfp"
        const val TAG2_BACKGROUND = "th#mp-tfp-head"
        const val TAG2_TITLE = "div#mp-tfp-h2"

        const val TAG3_DETAIL_AND_IMAGE = "td#mp-bm"
        const val TAG3_TITLE = "div#mp-bm-h2"

        const val TAG4_TAG5_BACKGROUND = "th#mp-itn-head"
        const val TAG4_TAG5_DETAIL_IMAGE = "td#mp-itn"
        const val TAG4_TAG5_TITLE = "div#mp-itn-h2"

        const val TAG5_DETAIL = "div.hlist-separated"

        const val TAG6_TITLE_AND_BACKGROUND = "th#mp-dyk-head"
        const val TAG6_DETAIL = "div#mf-dyk"
        const val TAG6_IMAGE = "td#mp-dyk"

        //Partners
        const val A = "a"
        const val TR = "tr"
        const val IMG = "img"
        const val SRC = "src"
        const val HTTPS = "https:"
    }
}