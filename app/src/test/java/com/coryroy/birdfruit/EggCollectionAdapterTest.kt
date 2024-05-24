package com.coryroy.birdfruit.adapters

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.coryroy.birdfruit.data.EggCollection
import com.coryroy.birdfruit.viewmodels.EggCollectionViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class EggCollectionAdapterTest {

    private var viewModel = EggCollectionViewModel()

    @Mock
    private lateinit var observer: Observer<List<EggCollection>>

    private lateinit var adapter: EggCollectionAdapter

    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    private lateinit var lifecycle: Lifecycle

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.currentState = Lifecycle.State.RESUMED
        `when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)

        mockStatic(Looper::class.java).use { mockedLooper ->
            val mainLooperMock = mock(Looper::class.java)
            mockedLooper.`when`<Any> { Looper.getMainLooper() }.thenReturn(mainLooperMock)
        }

        adapter = EggCollectionAdapter(viewModel, lifecycleOwner)
        `when`(adapter.notifyDataSetChanged()).then { doNothing() }
        viewModel.eggCollectionList.observeForever(observer)
    }

    @Test
    fun testOnCreateViewHolder() {
        // TODO: Implement this test
    }

    @Test
    fun testOnBindViewHolder() {
        // TODO: Implement this test
    }

    @Test
    fun testGetItemCount() {
        val eggCollectionList = MutableLiveData<List<EggCollection>>()
        eggCollectionList.value = listOf(EggCollection("Monday", 5), EggCollection("Tuesday", 3))
        verify(observer).onChanged(eggCollectionList.value!!)
        assert(adapter.itemCount == 2)
    }
}