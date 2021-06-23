package com.raywenderlich.android.creaturemon.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureAttributes
import com.raywenderlich.android.creaturemon.model.CreatureGenerator
import com.raywenderlich.android.creaturemon.model.CreatureRepository
import com.raywenderlich.android.creaturemon.model.room.RoomRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CreatureViewModelTest {
    private lateinit var creatureViewModel: CreatureViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGenerator: CreatureGenerator

    @Mock
    lateinit var repository: CreatureRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        creatureViewModel = CreatureViewModel(mockGenerator, repository)
    }

    @Test
    fun testSetupCreature() {
        val attributes = CreatureAttributes(10, 3, 7)
        val stubCreature = Creature(attributes, 87, "test creature")
        //use the when to return the mock generator
        `when`(mockGenerator.generateCreature(attributes)).thenReturn(stubCreature)

        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 7

        creatureViewModel.updateCreature()

        assertEquals(stubCreature, creatureViewModel.creature)
    }

    //test for creature with blank attributes can not be saved
    @Test
    fun cantSaveCreatureWithBlankName() {
        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 7
        creatureViewModel.drawable = 1
        creatureViewModel.name = ""
        val canSaveCreature = creatureViewModel.canSaveCreature()

        assertEquals(false, canSaveCreature)
    }
}