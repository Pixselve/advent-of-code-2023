import kotlin.math.pow


data class Card(val id: Int, val ownHand: Set<Int>, val winningHand: Set<Int>) {
    fun matchingNumbers() = this.ownHand.intersect(this.winningHand).size
}

val gameRegex = Regex("Card\\s+(\\d+):\\s+(.+) \\|\\s+(.+)")
val whiteSpaceRegex = Regex("\\s+")

fun parseCard(card: String): Card {
    val find = gameRegex.find(card)!!
    val (gameId, rowOwnHand, rawWinningHand) = find.destructured
    val winningHandSet = rawWinningHand.split(whiteSpaceRegex).map { it.toInt() }.toSet()
    val ownHandSet = rowOwnHand.split(whiteSpaceRegex).map { it.toInt() }.toSet()
    return Card(gameId.toInt(), ownHandSet, winningHandSet)
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { parseCard(it) }
            .sumOf { 2.0.pow(it.matchingNumbers() - 1).toInt() }
    }

    fun part2(input: List<String>): Int {
        val matchingNumbers = input.map { parseCard(it) }.map { it.matchingNumbers() }
        val ids = (matchingNumbers.indices).toMutableList()
        var i = 0;
        while (i < ids.size) {
            val id = ids[i]
            repeat(matchingNumbers[id]) {
                ids.add(id + it + 1)
            }
            i++
        }
        return ids.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    part1(testInput).println()
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
