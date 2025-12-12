package com.example.connectio.ui.game

import java.util.UUID
import kotlin.random.Random.Default.nextInt

enum class MergeableType { EMPTY, NUMBER, LETTER }

data class MergeableItem(
    val type: MergeableType,
    val level: Int = 0,
    val id: String = UUID.randomUUID().toString()
) {
    val display: String
        get() = when (type) {
            MergeableType.NUMBER -> level.toString()
            MergeableType.LETTER -> ('A' + level - 1).toString()
            MergeableType.EMPTY -> ""
        }
}

class NumberGenerator(
    val type: MergeableType = MergeableType.NUMBER,
    var level: Int = 1
) {
    fun determineGenerationLevel(): Int {
        return nextInt(from = 1, until = level)
    }
}

class LetterGenerator(
    val type: MergeableType = MergeableType.LETTER,
    var level: Int = 0
) {
    fun determineGenerationLevel(): Int {
        return nextInt(from = 1, until = level)
    }
}