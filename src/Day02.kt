class Hand(toParse: String) {
    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0

    init {
        toParse.split(", ").forEach { colorQuantity ->
            run {
                val split = colorQuantity.split(" ")
                val quantity = split.first().toInt()
                val color = split.last()
                when (color) {
                    "green" -> this.green = quantity
                    "blue" -> this.blue = quantity
                    "red" -> this.red = quantity
                }
            }
        }
    }

    override fun toString(): String {
        return "Hand(red=$red, green=$green, blue=$blue)"
    }

    fun isPossible(red: Int, green: Int, blue: Int): Boolean {
        return red >= this.red && blue >= this.blue && green >= this.green
    }


}
class Game(toParse: String) {
    var id: Int
    var hands: List<Hand>
    init {
        val split = toParse.split(": ")
        hands = split.last().split("; ").map { el -> Hand(el) }
        id = split.first().split(" ").last().toInt()
    }

    override fun toString(): String {
        return "Game(id=$id, hand=$hands)"
    }

    fun isPossible(red: Int, green: Int, blue: Int): Boolean {
        return this.hands.none { hand -> !hand.isPossible(red, green, blue) }
    }

    fun getMaxRed(): Int {
        return hands.maxOf { hand -> hand.red }
    }
    fun getMaxGreen(): Int {
        return hands.maxOf { hand -> hand.green }
    }
    fun getMaxBlue(): Int {
        return hands.maxOf { hand -> hand.blue }
    }

    fun power(): Int {
        return getMaxRed() * getMaxBlue() * getMaxGreen()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val maxRed = 12
        val maxGreen = 13
        val maxBlue = 14
        return input
            .map { line -> Game(line) }
            .filter { game -> game.isPossible(maxRed, maxGreen, maxBlue) }
            .map { game -> game.id }
            .reduce { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line -> Game(line) }
            .map { game -> game.power() }
            .reduce { acc, i -> acc + i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()

}
