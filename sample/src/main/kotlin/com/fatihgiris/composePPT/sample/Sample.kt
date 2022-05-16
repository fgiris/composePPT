package com.fatihgiris.composePPT.sample

import androidx.compose.runtime.LaunchedEffect
import com.fatihgiris.composePPT.foundation.list.List
import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.foundation.text.TextStyle
import com.fatihgiris.composePPT.runComposePPT
import kotlinx.coroutines.*
import java.awt.Color

fun main() {
    runComposePPT(presentationFileName = "composePPT") {
        Slide(title = "ComposePPT") {

            LaunchedEffect(Unit) {
                delay(100)
                println("You can use side effects with composePPT 💪")
            }

            List {
                Text(
                    text = "ComposePPT is a UI toolkit to create PowerPoint " +
                            "presentations with Compose 🤩"
                )
                Text(
                    text = "ComposePPT uses Compose Runtime under the hood."
                )
            }
        }

        Slide(title = "runComposePPT") {
            Text(
                text = "You can create as many presentations as " +
                        "you wish using runComposePPT."
            )
        }

        Slide {
            Text(
                text = "You can have a title for the slide as the previous slides " +
                        "or have a slide without a title just like this slide."
            )
        }

        Slide(title = "TextStyle") {
            List {
                Text(text = "You can give a custom style to the text using the TextStyle")

                Text(text = "Here is a text with defaults")

                Text(
                    text = "Here is a text with font size 40",
                    style = TextStyle(fontSize = 40f)
                )

                Text(
                    text = "Here is a text with font size 30 and font color magenta",
                    style = TextStyle(
                        fontSize = 30f,
                        fontColor = Color.MAGENTA
                    )
                )
            }
        }
    }
}