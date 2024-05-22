package com.coryroy.birdfruit.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coryroy.birdfruit.data.EggCollection

class EggCollectionViewModel : ViewModel() {
    val eggCollectionList: MutableLiveData<List<EggCollection>> = MutableLiveData(emptyList())
}