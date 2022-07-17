package com.codecodecoffee.compose.timeline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.codecodecoffee.compose.timeline.core.DefaultNode
import com.codecodecoffee.compose.timeline.core.LocalTimelineConfig
import com.codecodecoffee.compose.timeline.core.TimelineConfig
import com.codecodecoffee.compose.timeline.core.TimelineView
import com.codecodecoffee.compose.timeline.core.TimelineView.SingleNode
import com.codecodecoffee.compose.timeline.theme.ComposeTimelineTheme

import com.codecodecoffee.compose.timeline.theme.lineColor


data class TimelineItem(val title: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timelines = listOf<TimelineItem>(
            TimelineItem("Title 1", "Description 1"),
            TimelineItem("Title 2", "Description 2"),
            TimelineItem("Title 3", "Description 3"),
            TimelineItem("Title 4", "Description 4"),
            TimelineItem("Title 5", "Description 5"),
            TimelineItem("Title 6", "Description 6"),
            TimelineItem("Title 7", "Description 7"),
            TimelineItem("Title 8", "Description 8"),
            TimelineItem("Title 9", "Description 9"),
            TimelineItem("Title 10", "Description 10"),
        )

        setContent {
            ComposeTimelineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFf7f7f7)),
                ) {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                    ) {



                        CompositionLocalProvider(
                            LocalTimelineConfig provides TimelineConfig().copy(
                                lineColor = Color.DarkGray,
                                contentPosition = TimelineView.ContentPosition.End
                            )
                        ) {

                            SingleNode(
                                nodeType = TimelineView.NodeType.SPACER,


                                )

                            timelines.forEachIndexed { index, item ->
                                SingleNode(position = index, node = {

                                    when (index) {
                                        0 -> {
                                            Icon(
                                                imageVector = Icons.Default.AddCircle,
                                                contentDescription = "",
                                                modifier = Modifier

                                                    .background(
                                                        color = Color.White, shape = CircleShape
                                                    )
                                                    .clip(CircleShape)
                                                    .size(51.dp),
                                                tint = Color.Gray
                                            )
                                        }
                                        3 -> {
                                            Icon(
                                                imageVector = Icons.Default.AddCircle,
                                                contentDescription = "",
                                                modifier = Modifier

                                                    .background(
                                                        color = Color.White, shape = CircleShape
                                                    )
                                                    .clip(CircleShape)
                                                    .size(51.dp),
                                                tint = Color.Gray
                                            )
                                        }
                                        4 -> {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "",
                                                modifier = Modifier

                                                    .background(
                                                        color = Color.White, shape = CircleShape
                                                    )
                                                    .clip(CircleShape)
                                                    .size(51.dp),
                                                tint = Color(0xFF006400)
                                            )
                                        }
                                        else -> {
                                            DefaultNode(
                                                nodeColor = Color.White,
                                                nodeSize = LocalTimelineConfig.current.nodeSize
                                            )
                                        }
                                    }


                                }) {
                                    Column() {
                                        Text(text = item.title, fontWeight = FontWeight.SemiBold)
                                        Text(text = item.description)
                                    }
                                }
                            }

                            SingleNode(
                                nodeType = TimelineView.NodeType.SPACER,

                                )
                        }


                    }
                }


            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTimelineTheme {
        Greeting("Android")
    }
}