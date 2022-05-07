package com.fatihgiris.composePPT.sample

import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.foundation.list.List
import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.runComposePPT

fun main() {
    runComposePPT(presentationFileName = "firstPresentation") {
        Slide {
            List { Text(text = "Hello from the first slide") }
        }
        Slide {
            List { Text(text = "Hello from the second slide") }
        }
    }

    runComposePPT(presentationFileName = "secondPresentation") {
        Slide {
            List { Text(text = "Hello from the first slide") }
        }
        Slide {
            List { Text(text = "Hello from the second slide") }
        }
    }
}