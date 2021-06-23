package com.raywenderlich.android.creaturemon.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

open class CreatureViewModel(private val generator: CreatureGenerator = CreatureGenerator(),
                             private val repository: CreatureRepository = RoomRepository()) : ViewModel() {
    private val creatureLiveData = MutableLiveData<Creature>()
    private val saveLiveData = MutableLiveData<Boolean>()

    fun getCreatureLiveData(): LiveData<Creature> = creatureLiveData
    fun getSaveLiveData() = saveLiveData

    var name = ObservableField("")
    var intelligence = 0
    var strength = 0
    var endurance = 0
    var drawable = 0

    lateinit var creature: Creature

    fun updateCreature() {
        val attributes = CreatureAttributes(intelligence, strength, endurance)
        creature = generator.generateCreature(attributes, name.get() ?: "", drawable)
        creatureLiveData.postValue(creature)
    }

    fun attributeSelected(attributeType: AttributeType, position: Int) {
        when (attributeType) {
            AttributeType.INTELLIGENCE ->
                intelligence = AttributeStore.INTELLIGENCE[position].value
            AttributeType.STRENGTH ->
                strength = AttributeStore.STRENGTH[position].value
            AttributeType.ENDURANCE ->
                endurance = AttributeStore.ENDURANCE[position].value
        }
        updateCreature()
    }

    fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        updateCreature()
    }

    fun saveCreature() {
        if (canSaveCreature()) {
            repository.saveCreature(creature)
            saveLiveData.postValue(true)
        } else saveLiveData.postValue(false)
    }

    fun canSaveCreature(): Boolean {
        val name = name.get()
        name?.let {
            return intelligence != 0 && endurance != 0 && strength != 0 &&
                    drawable != 0 && name.isNotEmpty()
        }
        return false
    }
}