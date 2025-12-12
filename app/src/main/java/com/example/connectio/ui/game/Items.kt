package com.example.connectio.ui.game

import androidx.compose.runtime.Immutable
import java.util.UUID
import kotlin.random.Random.Default.nextInt

enum class MergeableType { EMPTY, NUMBER, LETTER }

@Immutable
interface GameItem {
    val type: MergeableType
    val level: Int
    val id: String
    val display: String
}

@Immutable
data class MergeableItem(
    override val type: MergeableType,
    override val level: Int = 0,
    override val id: String = UUID.randomUUID().toString()
) : GameItem {
    override val display: String
        get() = when (type) {
            MergeableType.NUMBER -> level.toString()
            MergeableType.LETTER -> ('A' + level - 1).toString()
            MergeableType.EMPTY -> ""
        }
}

@Immutable
data class Generator(
    override val type: MergeableType,
    override val level: Int = 1,
    override val id: String = UUID.randomUUID().toString()
) : GameItem {
    fun generateItem(): MergeableItem {
        return MergeableItem(
            this.type,
            nextInt(from = 1, until = level + 1)
        )
    }

    override val display: String
        get() = when (type) {
            MergeableType.NUMBER -> "#"
            MergeableType.LETTER -> "X"
            MergeableType.EMPTY -> "?"
        }
}