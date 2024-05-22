package com.coryroy.birdfruit.adapters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coryroy.birdfruit.data.EggCollection
import com.coryroy.birdfruit.viewmodels.EggCollectionViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedConstruction
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EggCollectionAdapterTest {

    @Mock
    private lateinit var viewModel: EggCollectionViewModel

    @Mock
    private lateinit var observer: Observer<List<EggCollection>>

    private lateinit var adapter: EggCollectionAdapter

    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner

    @Before
    fun setup() {
        adapter = EggCollectionAdapter(viewModel, lifecycleOwner)
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