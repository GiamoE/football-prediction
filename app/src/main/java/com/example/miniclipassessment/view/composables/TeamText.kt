package com.example.miniclipassessment.view.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniclipassessment.R

@Composable
fun TeamText(text: String) {
    Text(
        text = " $text ",
        modifier = Modifier
            .width(dimensionResource(R.dimen.team_text_width)),
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        color = Color.White
    )
    Divider(
        color = Color.White,
        modifier = Modifier
            .fillMaxHeight()
            .width(dimensionResource(R.dimen.divider_width))
    )
}

@Preview (showBackground = true)
@Composable
fun TeamTextPreview() {
    TeamText("Hello world")
}
