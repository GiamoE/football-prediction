package com.example.miniclipassessment.view.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.miniclipassessment.R

@Composable
fun PlayButton(modifier: Modifier?, simulated: Boolean, onClick: () -> Unit) {
    var buttonText = ""
    Button(
        modifier = Modifier
            .height(dimensionResource(R.dimen.play_button_height))
            .width(dimensionResource(R.dimen.play_button_width)),
        shape = RoundedCornerShape(
            corner = CornerSize(dimensionResource(R.dimen.play_button_corner_radius))
        ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.osm_orange)
        )
    ) {
        buttonText = if (!simulated) {
            stringResource(R.string.button_simulate)
        } else {
            stringResource(R.string.button_simulated)
        }
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = dimensionResource(R.dimen.button_text_font_size).value.sp

        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayButtonPreview() {
    PlayButton(Modifier, simulated = true, onClick = { Unit })
}
