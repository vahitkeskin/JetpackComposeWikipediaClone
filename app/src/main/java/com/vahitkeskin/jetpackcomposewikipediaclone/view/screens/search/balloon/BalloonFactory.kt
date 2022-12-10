package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search.balloon

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.*
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import com.vahitkeskin.jetpackcomposewikipediaclone.R
import com.vahitkeskin.jetpackcomposewikipediaclone.datastore.SharedDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @authot: Vahit Keskin
 * creared on 10.12.2022
 */

class BalloonFactory : Balloon.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        val dataStore = SharedDataStore(context)
        return Balloon.Builder(context)
            .setText("Click to search")
            .setArrowSize(10)
            .setWidthRatio(0.5f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setMarginRight(12)
            .setMarginLeft(12)
            .setTextSize(15f)
            .setCornerRadius(8f)
            .setTextColorResource(R.color.white_87)
            .setIconDrawableResource(R.drawable.wikipedia_logo)
            .setBackgroundColorResource(R.color.skyBlue)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setIsVisibleOverlay(true)
            .setOverlayColorResource(R.color.overlay)
            .setOverlayPaddingResource(R.dimen.editBalloonOverlayPadding)
            .setBalloonHighlightAnimation(BalloonHighlightAnimation.SHAKE)
            .setOverlayShape(
                BalloonOverlayRoundRect(
                    R.dimen.editBalloonOverlayRadius,
                    R.dimen.editBalloonOverlayRadius
                )
            )
            .setLifecycleOwner(lifecycle)
            .setDismissWhenClicked(true)
            .setOnBalloonDismissListener {
                GlobalScope.launch {
                    dataStore.saveSearchPopup(0)
                }
            }.build()
    }
}
