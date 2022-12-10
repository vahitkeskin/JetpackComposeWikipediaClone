package com.vahitkeskin.jetpackcomposewikipediaclone.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spanned
import android.view.View
import androidx.annotation.MainThread
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.text.HtmlCompat
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonCenterAlign
import com.skydoves.balloon.balloon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

fun instantTime() : String {
    val date = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

fun String.detailTimestamp(): String {
    val format = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss'Z'", Locale.getDefault())
    return  format.parse(this)?.toString().orEmpty()
}

fun String.htmlSpesificArea(): Elements {
    return Jsoup.parse(this).select("#mp-itn-h2")
}

fun String.htmlToString(): Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

val String.color get() = Color(android.graphics.Color.parseColor(this))

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun slowlyOpeningAnim(): Float {
    var mVisible by remember { mutableStateOf(false) }
    val compossableScope = rememberCoroutineScope()

    val mAlpha: Float by animateFloatAsState(
        targetValue = if (mVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearEasing,
        )
    )

    compossableScope.launch {
        delay(100)
        mVisible = true
    }
    return mAlpha
}

fun String.htmlBackgroundColor() : String {
    var mGroup = ""
    val doc: Document = Jsoup.parse(this)
    val divs: Elements = doc.select("div")
    val p = Pattern.compile("(?<=solid #)(.*)(?=; padding: )")
    for (e in divs) {
        val m: Matcher = p.matcher(e.attributes().toString())
        while (m.find()) {
            mGroup = m.group()
        }
    }
    return mGroup
}