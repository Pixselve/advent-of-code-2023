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

    override fun toString() = "Hand(red=$red, green=$green, blue=$blue)"

    fun isPossible(red: Int, green: Int, blue: Int) =
        red >= this.red && blue >= this.blue && green >= this.green


}
class Game(toParse: String) {
    var id: Int
    var hands: List<Hand>
    init {
        val split = toParse.split(": ")
        hands = split.last().split("; ").map { Hand(it) }
        id = split.first().split(" ").last().toInt()
    }
    override fun toString(): String = "Game(id=$id, hand=$hands)"
    fun isPossible(red: Int, green: Int, blue: Int) = this.hands.all { it.isPossible(red, green, blue) }
    private fun getMaxRed() = hands.maxOf { it.red }
    private fun getMaxGreen() = hands.maxOf { it.green }
    private fun getMaxBlue() = hands.maxOf { it.blue }
    fun power() = getMaxRed() * getMaxBlue() * getMaxGreen()
}

fun main() {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    fun part1(input: List<String>) =
        input
        .map { Game(it) }
        .filter { it.isPossible(maxRed, maxGreen, maxBlue) }
        .sumOf { it.id }

    fun part2(input: List<String>) =
        input
        .map { Game(it) }
        .sumOf { it.power() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
