class HandOfCards(private val card: String, val type: Int, val cardsStrength: Long, val bid: Int) {
    override fun toString(): String {
        return "$card - $cardsStrength"
    }
}

fun String.toHandOfCards(bid: Int): HandOfCards {
    val charMap = mutableMapOf<Char, Int>()
    for (c in this) {
        charMap[c] = charMap.getOrDefault(c, 0) + 1
    }


    return when (charMap.size) {
        1 -> HandOfCards(this, 7, this.cardsStrength(), bid) // Five of a kind
        2 -> {
            val values = charMap.values.sorted()
            if (values[1] == 4) {
                HandOfCards(this, 6, this.cardsStrength(), bid) // Four of a kind
            } else {
                HandOfCards(this, 5, this.cardsStrength(), bid) // Full house
            }
        }
        3 -> {
            val values = charMap.values.sorted()
            if (values[2] == 3) {
                HandOfCards(this, 4, this.cardsStrength(), bid) // Three of a kind
            } else {
                HandOfCards(this, 3, this.cardsStrength(), bid) // Two pair
            }
        }
        4 -> HandOfCards(this, 2, this.cardsStrength(), bid) // One pair
        5 -> HandOfCards(this, 1, this.cardsStrength(), bid) // High card
        else -> error("")
    }
}

fun String.cardsStrength(): Long = this
        .map { it.cardStrength() }
        .fold("") {acc, c -> acc + c }
        .toLong()

fun Char.cardStrength(): Int {
    if (this.isDigit()) return this.digitToInt() * 10
    return when (this) {
        'A' -> 99
        'K' -> 98
        'Q' -> 97
        'J' -> 96
        'T' -> 95
        else -> error("")
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.split(" ") }
            .map { (a, b) -> a.toHandOfCards(b.toInt()) }
            .sortedBy { it.type * 10000000000 + it.cardsStrength }
            .mapIndexed{ index, handOfCards -> (index + 1) * handOfCards.bid }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    part1(testInput).println()
    check(part1(testInput) == 6440)
//    check(part2(testInput) == 0)
    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}

