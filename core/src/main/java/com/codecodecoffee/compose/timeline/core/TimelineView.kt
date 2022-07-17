package com.codecodecoffee.compose.timeline.core

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codecodecoffee.compose.timeline.core.SingleNodeDrawings.drawBottomLine
import com.codecodecoffee.compose.timeline.core.SingleNodeDrawings.drawSpacerLine
import com.codecodecoffee.compose.timeline.core.SingleNodeDrawings.drawTopLine

data class TimelineConfig(
    val lineColor: Color = Color.Black,
    val nodeColor: Color = Color.White,
    val nodeSize: Float = 50f,
    val lineHeight: Dp = 131.dp,
    val contentPosition: TimelineView.ContentPosition = TimelineView.ContentPosition.Alternating,
)

val LocalTimelineConfig = compositionLocalOf { TimelineConfig() }


object TimelineView {
    enum class NodeType {
        FIRST, MIDDLE, LAST, SPACER
    }

    enum class ContentPosition {
        Start, End, Alternating
    }


    @Composable
    fun SingleNode(
        position: Int = 0,
        nodeType: NodeType = NodeType.MIDDLE,
        contentPosition: ContentPosition = LocalTimelineConfig.current.contentPosition,
        lineColors: Brush,
        nodeColor: Color = LocalTimelineConfig.current.nodeColor,
        nodeSize: Float = LocalTimelineConfig.current.nodeSize,
        lineHeight: Dp = LocalTimelineConfig.current.lineHeight,
        node: @Composable () -> Unit = {
            DefaultNode(
                nodeColor = nodeColor, nodeSize = nodeSize
            )
        },
        content: @Composable () -> Unit = {}
    ) {
        InternalSingleNode(
            position,
            nodeType,
            contentPosition,
            lineHeight = lineHeight,
            lineColors = lineColors,
            node = node,
            content = content
        )
    }


    @Composable
    fun SingleNode(
        position: Int = 0,
        nodeType: NodeType = NodeType.MIDDLE,
        contentPosition: ContentPosition = LocalTimelineConfig.current.contentPosition,
        lineColor: Color = LocalTimelineConfig.current.lineColor,
        nodeColor: Color = LocalTimelineConfig.current.nodeColor,
        nodeSize: Float = LocalTimelineConfig.current.nodeSize,
        lineHeight: Dp = LocalTimelineConfig.current.lineHeight,
        isDashed: Boolean = false,
        node: @Composable () -> Unit = {
            DefaultNode(
                nodeColor = nodeColor, nodeSize = nodeSize
            )
        },
        content: @Composable () -> Unit = {}
    ) {
        InternalSingleNode(
            position,
            nodeType,
            contentPosition,
            lineColor,
            isDashed = isDashed,
            lineHeight = lineHeight,
            node = node,
            content = content
        )

    }


    @Composable
    private fun InternalSingleNode(
        position: Int = 0,
        nodeType: NodeType = NodeType.MIDDLE,
        contentPosition: ContentPosition = LocalTimelineConfig.current.contentPosition,
        lineColor: Color = LocalTimelineConfig.current.lineColor,
        lineColors: Brush? = null,
        nodeColor: Color = LocalTimelineConfig.current.nodeColor,
        nodeSize: Float = LocalTimelineConfig.current.nodeSize,
        lineHeight: Dp = LocalTimelineConfig.current.lineHeight,
        isDashed: Boolean = false,
        node: @Composable () -> Unit = {
            DefaultNode(
                nodeColor = nodeColor, nodeSize = nodeSize
            )
        },
        content: @Composable () -> Unit = {}
    ) {
        ConstraintLayout(
            modifier = Modifier
                .requiredHeight(lineHeight)
                .fillMaxWidth()
                .padding(horizontal = 17.dp)

        ) {

            val (timeline, details) = createRefs()
            val nodeRadius = nodeSize / 2

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .constrainAs(timeline) {
                        when (contentPosition) {
                            ContentPosition.Start -> start.linkTo(parent.start)
                            ContentPosition.End -> end.linkTo(parent.end)
                            ContentPosition.Alternating -> centerHorizontallyTo(parent)
                        }

                    }, contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier
                        .padding(horizontal = 17.dp)
                        .fillMaxHeight()
                ) {
                    if (lineColors == null) {
                        lineDrawing(
                            nodeType = nodeType,
                            isDashed = isDashed,
                            lineColor = lineColor,
                            nodeRadius = nodeRadius
                        )
                    } else {
                        lineDrawing(
                            nodeType = nodeType, lineColors = lineColors, nodeRadius = nodeRadius
                        )
                    }

                }



                Box(
                    modifier = Modifier.requiredWidth(71.dp),
                    contentAlignment = Alignment.Center
                ) {

                    if (nodeType != NodeType.SPACER) {
                        node()
                    } else {
                        Box(modifier = Modifier)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .requiredHeight(lineHeight)
                    .constrainAs(details) {
                        centerVerticallyTo(parent)
                        when (contentPosition) {
                            ContentPosition.Start -> start.linkTo(timeline.end)
                            ContentPosition.End -> end.linkTo(timeline.start)
                            ContentPosition.Alternating -> {
                                if (position % 2 == 0) {
                                    start.linkTo(timeline.end)
                                } else {
                                    end.linkTo(timeline.start)
                                }
                            }
                        }

                    }, contentAlignment = Alignment.Center
            ) {
                content()
            }

        }
    }

    private fun DrawScope.lineDrawing(
        nodeType: NodeType, lineColors: Brush, nodeRadius: Float
    ) {

        val lineWidth = (3f / 3.5f * nodeRadius).coerceAtMost(40f)

        when (nodeType) {
            NodeType.FIRST -> {
                drawBottomLine(lineColors, lineWidth, nodeRadius)
            }
            NodeType.MIDDLE -> {
                drawTopLine(lineColors, lineWidth, nodeRadius)
                drawBottomLine(lineColors, lineWidth, nodeRadius)
            }
            NodeType.LAST -> {
                drawTopLine(lineColors, lineWidth, nodeRadius)
            }
            NodeType.SPACER -> {
                drawSpacerLine(lineColors, lineWidth)
            }
        }

    }

    private fun DrawScope.lineDrawing(
        nodeType: NodeType, isDashed: Boolean, lineColor: Color, nodeRadius: Float
    ) {

        val lineWidth = (3f / 3.5f * nodeRadius).coerceAtMost(40f)

        when (nodeType) {
            NodeType.FIRST -> {
                drawBottomLine(isDashed, lineColor, lineWidth, nodeRadius)
            }
            NodeType.MIDDLE -> {
                drawTopLine(isDashed, lineColor, lineWidth, nodeRadius)
                drawBottomLine(isDashed, lineColor, lineWidth, nodeRadius)
            }
            NodeType.LAST -> {
                drawTopLine(isDashed, lineColor, lineWidth, nodeRadius)
            }
            NodeType.SPACER -> {
                drawSpacerLine(isDashed, lineColor, lineWidth)
            }
        }
    }

}


@Composable
fun DefaultNode(nodeColor: Color, nodeSize: Float) {
    Box(
        modifier = Modifier
            .shadow(
                9.dp, shape = CircleShape, clip = true
            )
            .background(color = nodeColor, shape = CircleShape)
            .clip(CircleShape)
            .size((nodeSize / 2).dp)
    )
}


