data class Node(val left: String, val right: String)

val regex = Regex("(.+) = \\((.+), (.+)\\)")

fun main() {
    fun part1(input: List<String>): Int {
        val directions = generateSequence { input.first().split("").filter(String::isNotEmpty).asSequence() }.flatten().iterator()
        val resultMap = input.drop(2).associate {
            val (from, left, right) = regex.find(it)?.destructured ?: error("could not extract regex")
            from to Node(left, right)
        }

        var start = "AAA"
        var count = 0
        while (start != "ZZZ") {
            val direction = directions.next()
            start = if (direction == "L") {
                resultMap[start]?.left ?: error("could not find left of $start")
            } else {
                resultMap[start]?.right ?: error("could not find right of $start")
            }
            count++;
        }

        return count
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2)
    val testInput2 = readInput("Day08_test2")
    check(part2(testInput2) == 6)
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
