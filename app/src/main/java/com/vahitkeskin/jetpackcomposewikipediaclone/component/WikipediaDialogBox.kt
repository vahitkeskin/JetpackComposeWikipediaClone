package com.vahitkeskin.jetpackcomposewikipediaclone.component

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vahitkeskin.jetpackcomposewikipediaclone.R

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */

@Composable
fun DeleteDialogBox(
    negativeButtonColor: Color = Color(0xFF35898F),
    positiveButtonColor: Color = Color(0xFFFF0000),
    spaceBetweenElements: Dp = 18.dp,
    context: Context = LocalContext.current.applicationContext
) {

    var dialogOpen by remember {
        mutableStateOf(true)
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = {
            dialogOpen = false
        }
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(0.92f),
                color = Color.Transparent // dialog background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .padding(top = 30.dp) // this is the empty space at the top
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(percent = 10)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(height = 36.dp))

                        Text(
                            text = "Delete?",
                            fontSize = 24.sp
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Are you sure, you want to delete the item?",
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements))

                        // buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            DialogButton(buttonColor = negativeButtonColor, buttonText = "No") {
                                Toast
                                    .makeText(context, "No Click", Toast.LENGTH_SHORT)
                                    .show()
                                dialogOpen = false
                            }
                            DialogButton(buttonColor = positiveButtonColor, buttonText = "Yes") {
                                Toast
                                    .makeText(context, "Yes Click", Toast.LENGTH_SHORT)
                                    .show()
                                dialogOpen = false
                            }
                        }

                        Spacer(modifier = Modifier.height(height = spaceBetweenElements * 2))
                    }

                    // delete icon
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        tint = positiveButtonColor,
                        modifier = Modifier
                            .background(color = Color.White, shape = CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = positiveButtonColor)
                            .padding(all = 16.dp)
                            .align(alignment = Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun DialogButton(
    cornerRadiusPercent: Int = 26,
    buttonColor: Color,
    buttonText: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(percent = cornerRadiusPercent)
            )
            .clickable {
                onDismiss()
            }
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 18.sp,
        )
    }
}