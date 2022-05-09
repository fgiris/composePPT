package com.fatihgiris.composePPT

/**
 * Content of the composePPT canvas which can be translated to the display.
 */
sealed interface ComposePPTCanvasContent {

    /**
     * A content which only has text.
     */
    class TextContent(
        val text: String
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
    class SlideContent(
        val content: ComposePPTCanvasContent
    ) : ComposePPTCanvasContent


    /**
     * A content which has list of [SlideContent].
     */
    class PresentationContent(
        val slides: List<SlideContent>
    ) : ComposePPTCanvasContent
}