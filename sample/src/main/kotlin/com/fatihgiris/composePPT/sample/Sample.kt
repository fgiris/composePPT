package com.fatihgiris.composePPT.sample

import androidx.compose.runtime.LaunchedEffect
import com.fatihgiris.composePPT.foundation.list.List
import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.runComposePPT
import kotlinx.coroutines.*

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
            List {
                Text(
                    text = "You can create as many presentations as " +
                            "you wish using runComposePPT."
                )
            }
        }

        Slide {
            List {
                Text(
                    text = "You can have a title for the slide as the previous slides " +
                            "or have a slide without a title just like this slide."
                )
            }
        }
    }
}