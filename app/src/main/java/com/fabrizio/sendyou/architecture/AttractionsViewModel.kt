package com.fabrizio.sendyou.architecture

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabrizio.sendyou.data.Attraction

class AttractionsViewModel: ViewModel() {

    private val repository =AttractionsRepository()


// HomeFragment
    val attractionListLiveData = MutableLiveData<ArrayList<Attraction>>()


// AttractionDetailFragment
    val selectedAttractionLiveData = MutableLiveData<Attraction>()


// Locatio selected on AttractionDetailFragment
    val locationSelectedLiveData = MutableLiveData<Attraction>()

    fun init(context: Context){
        val atracctionsList = repository.parseAttractions(context)
        attractionListLiveData.postValue(atracctionsList)
    }

    fun onAttractionSelected(attractionId : String)  {
        val attraction = attractionListLiveData.value?.find {
            it.id== attractionId
        } ?: return
        selectedAttractionLiveData.postValue(attraction)
    }

}