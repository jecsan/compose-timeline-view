# Jetpack Compose Timeline View

Based from https://github.com/yeocak/ComposableTimelineView

------

#### *Screenshots*

<img src="https://github.com/jecsan/compose-timeline-view/blob/main/sample_1.png " height="550"/>
<img src="https://github.com/jecsan/compose-timeline-view/blob/main/sample_2.png " height="550"/>
<img src="https://github.com/jecsan/compose-timeline-view/blob/main/sample_3.png " height="550"/>

------

#### *Usage:*

```kotlin

Column {

    SingleNode(
        nodeType = TimelineView.NodeType.SPACER,
        node = {
            //Use any composable as a node
        },
        content = {
            //Use any composable as a content
        }
    )
}


```

#### *Options*

```kotlin
  SingleNode(
    nodeType = TimelineView.NodeType.FIRST,
    nodeSize = 51f,
    nodeColor = Color(0xFFf7f7f7),
    lineHeight = 51.dp,
    lineColor = Color.Gray,
    contentPosition = TimelineView.ContentPosition.Alternating,
    node = {},//Can also be a default node
    isDashed = false,
    content = {}
)
```

-------------

#### *How to add to my project:*

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.jecsan:compose-timeline-view:v0.1.1'
}

```
