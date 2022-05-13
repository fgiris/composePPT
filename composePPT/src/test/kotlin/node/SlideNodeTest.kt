package node

import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.runComposePPT
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class SlideNodeTest {

    @Test fun ensureSlideNodeInsideSlideNodeThrowsException() {
        assertThrows<IllegalArgumentException> {
            runComposePPT {
                Slide { Slide { Text("") } }
            }
        }
    }

    @Test fun ensureEmptyContentForSlideNodeThrowsException() {
        assertThrows<IllegalArgumentException> {
            runComposePPT {
                Slide {}
            }
        }
    }

    @Test fun ensureSlideNodeWithMultipleChildNodesThrowsException() {
        assertThrows<IllegalArgumentException> {
            runComposePPT {
                Slide {
                    Text("First node")
                    Text("Second node")
                }
            }
        }
    }

    @Test fun ensureSlideNodeWithSingleChildNodeDoesNotThrowException() {
        assertDoesNotThrow {
            runComposePPT {
                Slide {
                    Text("First node")
                }
            }
        }
    }
}