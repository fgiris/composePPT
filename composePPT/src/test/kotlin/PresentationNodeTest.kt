import com.fatihgiris.composePPT.foundation.slide.Presentation
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.runComposePPT
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PresentationNodeTest {

    @Test
    fun ensurePresentationNodeOnlyAllowsSlideNodes() {
        assertThrows<IllegalArgumentException> {
            runComposePPT {
                Presentation { Text("") }
            }
        }
    }
}