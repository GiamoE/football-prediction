package com.example.miniclipassessment.view.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniclipassessment.R

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.osm_tile),
        contentDescription = stringResource(R.string.header_image)
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderImagePreview() {
    HeaderImage(Modifier)
}