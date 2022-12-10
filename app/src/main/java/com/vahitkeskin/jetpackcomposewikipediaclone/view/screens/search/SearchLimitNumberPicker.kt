package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import android.widget.NumberPicker
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

/**
 * @authot: Vahit Keskin
 * creared on 10.12.2022
 */
@Composable
fun SearchLimitNumberPicker(
    modifier: Modifier = Modifier,
    stateNumberPicker: (Int) -> Unit
) {
    var mStateNumberPicker = 10
    AndroidView(
        modifier = modifier
            .width(50.dp)
            .height(80.dp)
            .padding(5.dp),
        factory = { context ->
            NumberPicker(context).apply { //For scroll request limit value, default 10
                textColor = ContextCompat.getColor(context, android.R.color.white)
                setOnValueChangedListener { numberPicker, _, _ ->
                    mStateNumberPicker = if (numberPicker.value > 0) numberPicker.value else 1
                }
                minValue = 0
                maxValue = 20
                value = mStateNumberPicker
            }
        }
    )
    stateNumberPicker.invoke(mStateNumberPicker)
}

@Preview
@Composable
fun SearchLimitNumberPickerPreview() {
    SearchLimitNumberPicker { }
}