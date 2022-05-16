import androidx.compose.runtime.*
import com.fatihgiris.composePPT.dispatchFrame
import com.fatihgiris.composePPT.foundation.slide.Slide
import com.fatihgiris.composePPT.foundation.text.Text
import com.fatihgiris.composePPT.runComposePPT
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.io.File

class ComposePPTTest {

    @Test
    fun ensurePresentationFileNamedCorrectly() {
        val presentationName = "Test Presentation"

        runComposePPT(presentationName) {
            Slide { Text("") }
        }

        // Verify if the presentation exists with a given name
        assert(File("$presentationName.pptx").exists())
    }

    @Test
    fun launchedEffectCompletesBeforeTermination() {
        var isLaunchedEffectFinished = false

        runComposePPT {
            LaunchedEffect(Unit) {
                delay(3000)
                isLaunchedEffectFinished = true
            }
        }

        assert(isLaunchedEffectFinished)
    }

    @Test
    fun disposableEffectCompletesBeforeTermination() {
        var isDisposableEffectExecuted = false

        runComposePPT {
            DisposableEffect(Unit) {
                onDispose {
                    isDisposableEffectExecuted = true
                }
            }
        }

        assert(isDisposableEffectExecuted)
    }

    @Test
    fun sideEffectRunsAsManyAsRecomposition() {
        val expectedRecomposition = 2
        var actualRecomposition = 0

        runComposePPT {
            Slide {
                var text by remember { mutableStateOf("Initial composition") }

                LaunchedEffect(Unit) {
                    delay(1000)
                    text = "After recomposition"
                }

                SideEffect { actualRecomposition++ }

                Text(text)
            }
        }

        assert(actualRecomposition == expectedRecomposition)
    }

    @Test
    fun valueRemembered() {
        // The reason of the number being mutable is to be able to change the
        // value in the same object without changing the object itself.
        data class MutableRememberModel(var number: Int)

        val expectedRememberedNumber = 1
        lateinit var actualRememberedValue: MutableRememberModel

        runComposePPT {
            // Create a value without a state not to trigger a recomposition
            val rememberedValue = remember { MutableRememberModel(0) }

            var valueToTriggerRecomposition by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(Unit) {
                delay(1500)
                rememberedValue.number = expectedRememberedNumber
                valueToTriggerRecomposition = true
            }

            // A dummy composable to read the state value
            Slide { Text(valueToTriggerRecomposition.toString()) }

            DisposableEffect(Unit) {
                onDispose {
                    actualRememberedValue = rememberedValue
                }
            }
        }

        assert(actualRememberedValue.number == expectedRememberedNumber)
    }

    @Test
    fun dispatchFrameSendsTimeNanosToFrameAwaiters() {
        runBlocking {
            val frameClock = BroadcastFrameClock()
            val expectedTimeNanos = 1234L

            // Send the expected time nanos to the frame awaiters
            launch {
                frameClock.dispatchFrame(
                    refreshRateMillis = 10,
                    timeNanos = { expectedTimeNanos }
                )
            }

            launch(frameClock) {
                withFrameNanos { actualTimeNanos ->
                    assert(actualTimeNanos == expectedTimeNanos)

                    // End the test
                    this@runBlocking.coroutineContext.cancelChildren()
                }
            }
        }
    }
}