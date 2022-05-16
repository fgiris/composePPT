package com.fatihgiris.composePPT

import com.fatihgiris.composePPT.foundation.text.TextStyle

/**
 * Content of the composePPT canvas which can be translated to the display.
 */
sealed interface ComposePPTCanvasContent {

    /**
     * A content which only has text.
     */
    class TextContent(
        val text: String,
        val style: TextStyle
    ) : ComposePPTCanvasContent

    /**
     * A content which has a list of [ComposePPTCanvasContent].
     */
    class ListContent(
        val contentList: List<ComposePPTCanvasContent>
    ) : ComposePPTCanvasContent

    /**
     * A content which has a single [ComposePPTCanvasContent].
     */
    sealed class SlideContent(
        open val content: ComposePPTCanvasContent
    ) : ComposePPTCanvasContent {
        class TitleAndBodyContent(
            val title: String,
            override val content: ComposePPTCanvasContent
        ) : SlideContent(content)

        class OnlyBodyContent(
            override val content: ComposePPTCanvasContent
        ) : SlideContent(content)
    }


    /**
     * A content which has list of [SlideContent].
     */
    class PresentationContent(
        val slides: List<SlideContent>
    ) : ComposePPTCanvasContent
}