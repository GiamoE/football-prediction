package com.example.miniclipassessment.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.miniclipassessment.R
import com.example.miniclipassessment.ui.theme.MiniclipassessmentTheme
import com.example.miniclipassessment.view.composables.DisplayTeams
import com.example.miniclipassessment.view.composables.HeaderImage
import com.example.miniclipassessment.view.composables.PlayButton
import com.example.miniclipassessment.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniclipassessmentTheme {
                MainColumn()
            }
        }
        model.initialSetup()
    }

    @Composable
    fun MainColumn() {
        var simulated by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background_sm),
                    contentScale = ContentScale.FillBounds
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderImage(
                Modifier.padding(
                        bottom = dimensionResource(R.dimen.header_image_padding_bottom),
                        top = dimensionResource(R.dimen.header_image_padding_top)
                    )
            )
            val headerText: String =
                if (simulated) resources.getString(R.string.simulated) else resources.getString(
                    R.string.play
                )
            Text(
                modifier = Modifier.height(dimensionResource(R.dimen.main_column_text_height)),
                text = headerText,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = dimensionResource(R.dimen.title_font_size).value.sp,
                color = Color.White,
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayTeams(model.generateResults(), simulated)
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.main_column_row_padding_bottom))
                ) {
                    PlayButton(modifier = Modifier, simulated = simulated, onClick = {
                        simulated = if (!simulated) {
                            model.playAllMatches()
                            true
                        } else {
                            model.reset()
                            false
                        }
                    })
                }
            }
        }
    }
}
