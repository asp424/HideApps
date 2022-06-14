package com.lm.hideapps

import androidx.test.platform.app.InstrumentationRegistry
import com.lm.hideapps.core.WeatherMapper
import com.lm.hideapps.data.local_repositories.BroadcastIntentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.lm.hideapps", appContext.packageName)
    }
}

@RunWith(JUnit4::class)
class BroadcastReceiverTest() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun receive() {
        CoroutineScope(IO).launch {
            BroadcastIntentRepository.Base().receiveBroadcastIntentsAsFlow(
                listOf("ass"), appContext
            ).collect {
                println(it)
            }
        }
    }
}

@RunWith(JUnit4::class)
class StringToSoundIdMapperTest() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun convert() {
            WeatherMapper.Base(appContext.resources, appContext.packageName)
                .stringToSoundId("-0.34")
    }
}

