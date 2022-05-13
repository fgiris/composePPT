package com.fatihgiris.composePPT.graphics

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import org.apache.poi.xslf.usermodel.SlideLayout
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFSlide
import org.apache.poi.xslf.usermodel.XSLFTextShape
import java.awt.Rectangle
import java.io.FileOutputStream

/**
 * A display which knows how to translate & draw [ComposePPTCanvasContent]. For instance,
 * if the display only supports displaying texts then it can translate the content to
 * text and draw it.
 */
internal interface Display {

    /**
     * Translates and draws the given [content] to the display unit.
     */
    fun display(content: ComposePPTCanvasContent)
}

/**
 * A logcat display to draw the [ComposePPTCanvasContent]. This display can be used for
 * debugging purposes.
 */
internal class LogcatDisplay : Display {

    override fun display(content: ComposePPTCanvasContent) {
        when (content) {
            is ComposePPTCanvasContent.TextContent -> {
                doDisplay(content)
            }
            is ComposePPTCanvasContent.ListContent -> {
                content.contentList.forEach {
                    display(it)
                }
            }
            is ComposePPTCanvasContent.SlideContent -> {
                display(content.content)
            }
            is ComposePPTCanvasContent.PresentationContent -> {
                content.slides.forEach {
                    display(it)
                }
            }
        }
    }

    private fun doDisplay(textContent: ComposePPTCanvasContent.TextContent) {
        println(textContent.text)
    }
}

/**
 * A display to write the [ComposePPTCanvasContent] as a ppt file.
 *
 * @param presentationFileName The name of the presentation without a file extension
 */
class ComposePPTDisplay(
    private val presentationFileName: String
) : Display {

    private val slideShow = XMLSlideShow()

    private lateinit var currentSlide: XSLFSlide

    override fun display(content: ComposePPTCanvasContent) {
        when (content) {
            is ComposePPTCanvasContent.TextContent -> {
                doDisplay(content)
            }
            is ComposePPTCanvasContent.ListContent -> {
                content.contentList.forEach {
                    display(it)
                }
            }
            is ComposePPTCanvasContent.SlideContent -> {
                createSlide(content)
                clearPlaceholdersText()
                setSlideTitle(content)
                display(content.content)
            }
            is ComposePPTCanvasContent.PresentationContent -> {
                content.slides.forEach {
                    display(it)
                }
            }
        }
    }

    private fun createSlide(slideContent: ComposePPTCanvasContent.SlideContent) {
        currentSlide = with(slideShow) {
            val slideLayout = when (slideContent) {
                is ComposePPTCanvasContent.SlideContent.OnlyBodyContent -> SlideLayout.BLANK
                is ComposePPTCanvasContent.SlideContent.TitleAndBodyContent -> {
                    SlideLayout.TITLE_AND_CONTENT
                }
            }

            val layout = slideMasters[0].getLayout(slideLayout)

            // Only single slide is supported for now
            createSlide(layout)
        }
    }

    private fun setSlideTitle(slideContent: ComposePPTCanvasContent.SlideContent) {
        if (slideContent is ComposePPTCanvasContent.SlideContent.TitleAndBodyContent) {
            currentSlide.placeholders[0].text = slideContent.title
        }
    }

    private fun clearPlaceholdersText() {
        // There is a default text in the placeholders. So clear them.
        currentSlide.placeholders.forEach { it.text = "" }
    }

    private fun doDisplay(content: ComposePPTCanvasContent.TextContent) {
        // Add new paragraph to the body with a given text content
        getTextBox().addNewTextParagraph().addNewTextRun().setText(content.text)
        writeSlideShowToFile()
    }

    private fun getTextBox(): XSLFTextShape {
        return if (currentSlide.placeholders.isNotEmpty()) {
            // Title and body slide
            currentSlide.placeholders[1]
        } else {
            // Blank slide
            currentSlide.createTextBox().also {
                it.clearText()

                // Currently, positioning to the top left
                it.anchor = getTopLeftRectangle()
            }
        }
    }

    private fun getTopLeftRectangle(): Rectangle {
        return Rectangle(
            10,
            10,
            slideShow.pageSize.width,
            slideShow.pageSize.height
        )
    }

    private fun writeSlideShowToFile() {
        val fos = FileOutputStream("$presentationFileName.pptx")
        slideShow.write(fos)
        fos.close()
    }
}