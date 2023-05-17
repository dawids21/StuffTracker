package xyz.stasiak.stufftracker.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ClockAnimation(size: Dp, modifier: Modifier = Modifier) {
    val hourPosition = remember {
        Animatable(0f)
    }
    val minutePosition = remember {
        Animatable(0f)
    }
    val minuteTime = 2000
    LaunchedEffect(Unit) {
        launch {
            minutePosition.animateTo(
                360f, animationSpec = infiniteRepeatable(
                    animation = tween(minuteTime, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        launch {
            hourPosition.animateTo(
                360f, animationSpec = infiniteRepeatable(
                    animation = tween(minuteTime * 12, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }
    val clockColor = MaterialTheme.colorScheme.primary
    val minuteHourColor = MaterialTheme.colorScheme.onPrimary
    Canvas(modifier = modifier.size(size),
        onDraw = {
            val sizePx = size.toPx()
            drawCircle(
                color = clockColor,
            )
            rotate(hourPosition.value) {
                drawRoundRect(
                    color = minuteHourColor,
                    topLeft = Offset(sizePx / 2 - sizePx / 24, sizePx / 6 + sizePx / 12),
                    size = Size(sizePx / 12, sizePx / 3),
                    cornerRadius = CornerRadius(16f)
                )
            }
            rotate(minutePosition.value) {
                drawRoundRect(
                    color = minuteHourColor,
                    topLeft = Offset(sizePx / 2 - sizePx / 24, sizePx / 12),
                    size = Size(sizePx / 12, sizePx / 2),
                    cornerRadius = CornerRadius(16f)
                )
            }
        }
    )
}

@Preview
@Composable
fun ClockAnimationPreview() {
    ClockAnimation(200.dp)
}
