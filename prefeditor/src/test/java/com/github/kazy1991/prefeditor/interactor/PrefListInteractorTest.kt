package com.github.kazy1991.prefeditor.interactor

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PrefListInteractorTest {

    private val context = RuntimeEnvironment.application
    private val testPrefName = "sample"
    private lateinit var prefInteractor: PrefListInteractor

    @Before
    fun init() {
        prefInteractor = PrefListInteractor(context, testPrefName)
    }

    @Ignore
    @Test
    fun keyValuePairsTest() {

    }

}