package com.fatihgiris.composePPT.sample

import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.foundation.list.List
import com.fatihgiris.composePPT.runComposePPT

fun main() {
    runComposePPT(presentationFileName = "firstPresentation") {
        Text(text = "Hello from the first line!")
        Text(text = "Hello from the second line!")
        List { Text(text = "Hello from the list") }
    }

    runComposePPT(presentationFileName = "secondPresentation") {
        Text(text = "Hello from the first line!")
        Text(text = "Hello from the second line!")
        List { Text(text = "Hello from the list") }
    }
}