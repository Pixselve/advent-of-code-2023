
data class Race(val time: Long, val distance: Long) {
    fun winningGamesCount(): Long {
        var count = 0L
        for (holdTime in time downTo 0) {
            if (isGameWinning(time, holdTime, distance)) {
                count++
            }
        }
        return count
    }
}

fun isGameWinning(maxTime: Long, holdingTime: Long, distanceToBeat: Long): Boolean {
    val remainingTime = maxTime - holdingTime
    return remainingTime * holdingTime > distanceToBeat
}


fun main() {
    fun part1(input: List<String>): Long {
        val times = input[0].split("\\s+".toRegex()).drop(1).map { it.toLong() }
        val distances = input[1].split("\\s+".toRegex()).drop(1).map { it.toLong() }
        return times
            .zip(distances)
            .map { Race(it.first, it.second) }
            .map { it.winningGamesCount() }
            .reduce { acc, unit -> acc * unit }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].replace(" ", "").substringAfter(":").toLong()
        val distance = input[1].replace(" ", "").substringAfter(":").toLong()

        return Race(time, distance).winningGamesCount()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
