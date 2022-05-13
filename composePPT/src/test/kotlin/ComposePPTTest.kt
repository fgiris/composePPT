import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.runComposePPT
import org.junit.jupiter.api.Test
import java.io.File

class ComposePPTTest {

    @Test fun ensurePresentationFileNamedCorrectly() {
        val presentationName = "Test Presentation"

        runComposePPT(presentationName) {
            Slide { Text("") }
        }

        // Verify if the presentation exists with a given name
        assert(File("$presentationName.pptx").exists())
    }
}