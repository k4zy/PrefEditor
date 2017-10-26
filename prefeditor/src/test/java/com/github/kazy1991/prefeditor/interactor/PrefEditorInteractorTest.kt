package com.github.kazy1991.prefeditor.interactor

import android.content.Context
import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PrefEditorInteractorTest {

    private val context = RuntimeEnvironment.application
    private lateinit var prefInteractor: PrefEditorInteractor

    @Before
    fun init() {
        prefInteractor = PrefEditorInteractor(context)
    }

    @Test
    fun schemaItemsTest() {
        //Given
        val prefNameSet = setOf("sample1", "sample2")
        prefNameSet.forEach {
            val pref = context.getSharedPreferences(it, Context.MODE_PRIVATE)
            pref.edit().putBoolean("test_key", true).commit()
        }
        val observer: TestObserver<List<SchemaItem>> = TestObserver.create()

        //When
        prefInteractor.schemaItems().subscribe(observer)

        //Then
        val expected = prefNameSet.map { SchemaItem(it) }
        assertThat(observer.values().first()).isEqualTo(expected)
        observer.assertTerminated()
    }

    @Test
    fun convertToNavigationItemTest() {
        //Given
        val defaultPrefName = prefInteractor.defaultPrefName
        val defaultPrefAnnotatedName = prefInteractor.defaultPrefAnnotatedName
        //When
        val actual = prefInteractor.convertToNavigationItem(defaultPrefName)
        //Then
        assertThat(actual).isEqualTo(SchemaItem(defaultPrefName, defaultPrefAnnotatedName))
    }

    @Test
    fun sortPrefList() {
        //Given
        val prefNameList = listOf("sample1", "sample2", prefInteractor.defaultPrefName)
        //When
        val actual = prefInteractor.sortPrefList(prefNameList)
        //Then
        val expected = listOf(prefInteractor.defaultPrefName, "sample1", "sample2")
        assertThat(actual).isEqualTo(expected)
    }

}
