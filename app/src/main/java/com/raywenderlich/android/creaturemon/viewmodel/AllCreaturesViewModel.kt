package com.raywenderlich.android.creaturemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureRepository
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class AllCreaturesViewModel(private val repository: CreatureRepository = RoomRepository()) : ViewModel() {

    private val allCreaturesLiveData = repository.getAllCreatures()

    fun getAllCreatures() = allCreaturesLiveData

    fun clearAllCreatures() = repository.clearAllCreatures()


}