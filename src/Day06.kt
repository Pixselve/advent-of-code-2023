import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun winningGamesCount(time: Long, distance: Long): Int {
    val x1 = (-time - sqrt((time * time - 4 * distance).toDouble())) / (-2)
    val x2 = (-time + sqrt((time * time - 4 * distance).toDouble())) / (-2)
    return ceil(x1).toInt() - floor(x2).toInt() - 1
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split("\\s+".toRegex()).drop(1).map(String::toLong)  }
            .let { (times, distances) -> times.zip(distances) }
            .map { winningGamesCount(it.first, it.second) }
            .reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.replace(" ", "").substringAfter(":").toLong() }
            .let { (time, distance) -> winningGamesCount(time, distance) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    part1(testInput).println()
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
