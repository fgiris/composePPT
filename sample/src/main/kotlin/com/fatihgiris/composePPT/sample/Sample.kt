package com.fatihgiris.composePPT.sample

import com.fatihgiris.composePPT.foundation.list.List
import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.runComposePPT

fun main() {
    runComposePPT(presentationFileName = "composePPT") {
        Slide(title = "ComposePPT") {
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